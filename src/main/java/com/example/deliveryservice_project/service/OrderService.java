package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.OrderCreateDto;
import com.example.deliveryservice_project.dto.OrderResponseDto;
import com.example.deliveryservice_project.order.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderCreateDto dto);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getOrders(Long clientId, Long restaurantId, Long courierId);

    OrderResponseDto updateStatus(Long id, OrderStatus newStatus);

    OrderResponseDto assignCourier(Long id, Long courierId);
}