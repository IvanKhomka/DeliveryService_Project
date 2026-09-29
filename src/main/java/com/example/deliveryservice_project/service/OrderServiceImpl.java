package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.OrderCreateDto;
import com.example.deliveryservice_project.dto.OrderItemCreateDto;
import com.example.deliveryservice_project.dto.OrderItemResponseDto;
import com.example.deliveryservice_project.dto.OrderResponseDto;
import com.example.deliveryservice_project.exception.*;
import com.example.deliveryservice_project.model.Dish;
import com.example.deliveryservice_project.model.Order;
import com.example.deliveryservice_project.model.OrderItem;
import com.example.deliveryservice_project.model.User;
import com.example.deliveryservice_project.order.OrderStatus;
import com.example.deliveryservice_project.repository.*;
import com.example.deliveryservice_project.roles.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS = Map.of(
            OrderStatus.NEW, Set.of(OrderStatus.CONFIRMED, OrderStatus.CANCELLED),
            OrderStatus.CONFIRMED, Set.of(OrderStatus.PREPARING, OrderStatus.CANCELLED),
            OrderStatus.PREPARING, Set.of(OrderStatus.ON_THE_WAY, OrderStatus.CANCELLED),
            OrderStatus.ON_THE_WAY, Set.of(OrderStatus.DELIVERED),
            OrderStatus.DELIVERED, Set.of(),
            OrderStatus.CANCELLED, Set.of()
    );

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final DishRepository dishRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            RestaurantRepository restaurantRepository,
                            AddressRepository addressRepository,
                            DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderCreateDto dto) {
        if (dto.items() == null || dto.items().isEmpty()) {
            throw new EmptyOrderException();
        }
        if (!userRepository.existsById(dto.clientId())) {
            throw new UserNotFoundException(dto.clientId());
        }
        if (!restaurantRepository.existsById(dto.restaurantId())) {
            throw new RestaurantNotFoundException(dto.restaurantId());
        }
        if (!addressRepository.existsById(dto.deliveryAddressId())) {
            throw new AddressNotFoundException(dto.deliveryAddressId());
        }

        Order order = new Order(dto.clientId(), dto.restaurantId(), dto.deliveryAddressId());

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemCreateDto itemDto : dto.items()) {
            if (itemDto.quantity() == null || itemDto.quantity() <= 0) {
                throw new IllegalArgumentException(
                        "Количество для блюда с id=" + itemDto.dishId() + " должно быть положительным");
            }

            Dish dish = dishRepository.findById(itemDto.dishId())
                    .orElseThrow(() -> new DishNotFoundException(itemDto.dishId()));

            if (!dish.getRestaurantId().equals(dto.restaurantId())) {
                throw new DishNotInRestaurantException(dish.getId(), dto.restaurantId());
            }

            OrderItem item = new OrderItem(dish.getId(), itemDto.quantity(), dish.getPrice());
            order.addItem(item);

            total = total.add(dish.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity())));
        }
        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);
        return toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        return toResponseDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrders(Long clientId, Long restaurantId, Long courierId) {
        List<Order> orders;
        if (courierId != null) {
            orders = orderRepository.findByCourierId(courierId);
        } else if (restaurantId != null) {
            orders = orderRepository.findByRestaurantId(restaurantId);
        } else if (clientId != null) {
            orders = orderRepository.findByClientId(clientId);
        } else {
            orders = orderRepository.findAll();
        }
        return orders.stream().map(this::toResponseDto).toList();
    }

    @Override
    @Transactional
    public OrderResponseDto updateStatus(Long id, OrderStatus newStatus) {
        Order order = findOrThrow(id);
        Set<OrderStatus> allowed = ALLOWED_TRANSITIONS.getOrDefault(order.getStatus(), Set.of());
        if (!allowed.contains(newStatus)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus(), newStatus);
        }
        order.updateStatus(newStatus);
        return toResponseDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDto assignCourier(Long id, Long courierId) {
        Order order = findOrThrow(id);
        User courier = userRepository.findById(courierId)
                .orElseThrow(() -> new UserNotFoundException(courierId));
        if (courier.getRole() != UserRole.COURIER) {
            throw new UserNotCourierException(courierId);
        }
        order.assignCourier(courierId);
        return toResponseDto(order);
    }

    private Order findOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private OrderResponseDto toResponseDto(Order order) {
        List<OrderItemResponseDto> items = order.getItems().stream()
                .map(item -> {
                    String dishName = dishRepository.findById(item.getDishId())
                            .map(Dish::getName)
                            .orElse(null);
                    return new OrderItemResponseDto(
                            item.getDishId(), dishName, item.getQuantity(), item.getPriceAtPurchase());
                })
                .toList();

        return new OrderResponseDto(
                order.getId(),
                order.getClientId(),
                order.getRestaurantId(),
                order.getCourierId(),
                order.getDeliveryAddressId(),
                order.getStatus(),
                order.getTotalPrice(),
                items,
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getDeliveredAt()
        );
    }
}