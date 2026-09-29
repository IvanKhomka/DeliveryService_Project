package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.RestaurantCreateDto;
import com.example.deliveryservice_project.dto.RestaurantResponseDto;
import com.example.deliveryservice_project.dto.RestaurantUpdateDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResponseDto> getAllRestaurants();

    List<RestaurantResponseDto> getRestaurantsByOwner(Long ownerId);

    RestaurantResponseDto getRestaurantById(Long id);

    RestaurantResponseDto createRestaurant(RestaurantCreateDto dto);

    RestaurantResponseDto updateRestaurant(Long id, RestaurantUpdateDto dto);

    void deleteRestaurant(Long id);
}