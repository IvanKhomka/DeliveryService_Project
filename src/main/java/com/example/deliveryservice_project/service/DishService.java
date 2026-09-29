package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.DishCreateDto;
import com.example.deliveryservice_project.dto.DishResponseDto;
import com.example.deliveryservice_project.dto.DishUpdateDto;

import java.util.List;

public interface DishService {
    List<DishResponseDto> getDishesByRestaurant(Long restaurantId);

    DishResponseDto getDishById(Long id);

    DishResponseDto createDish(DishCreateDto dto);

    DishResponseDto updateDish(Long id, DishUpdateDto dto);

    void deleteDish(Long id);
}