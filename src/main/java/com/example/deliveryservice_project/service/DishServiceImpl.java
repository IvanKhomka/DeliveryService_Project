package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.DishCreateDto;
import com.example.deliveryservice_project.dto.DishResponseDto;
import com.example.deliveryservice_project.dto.DishUpdateDto;
import com.example.deliveryservice_project.exception.DishNotFoundException;
import com.example.deliveryservice_project.exception.RestaurantNotFoundException;
import com.example.deliveryservice_project.model.Dish;
import com.example.deliveryservice_project.repository.DishRepository;
import com.example.deliveryservice_project.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishServiceImpl(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DishResponseDto> getDishesByRestaurant(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RestaurantNotFoundException(restaurantId);
        }
        return dishRepository.findByRestaurantId(restaurantId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DishResponseDto getDishById(Long id) {
        return toResponseDto(findOrThrow(id));
    }

    @Override
    @Transactional
    public DishResponseDto createDish(DishCreateDto dto) {
        if (!restaurantRepository.existsById(dto.restaurantId())) {
            throw new RestaurantNotFoundException(dto.restaurantId());
        }
        Dish dish = new Dish(dto.restaurantId(), dto.name(), dto.price(), dto.description());
        return toResponseDto(dishRepository.save(dish));
    }

    @Override
    @Transactional
    public DishResponseDto updateDish(Long id, DishUpdateDto dto) {
        Dish dish = findOrThrow(id);
        dish.update(dto.name(), dto.price(), dto.description());
        return toResponseDto(dish);
    }

    @Override
    @Transactional
    public void deleteDish(Long id) {
        dishRepository.delete(findOrThrow(id));
    }

    private Dish findOrThrow(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(id));
    }

    private DishResponseDto toResponseDto(Dish dish) {
        return new DishResponseDto(
                dish.getId(), dish.getRestaurantId(), dish.getName(), dish.getPrice(), dish.getDescription());
    }
}