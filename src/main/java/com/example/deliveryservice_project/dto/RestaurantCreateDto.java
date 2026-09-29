package com.example.deliveryservice_project.dto;

public record RestaurantCreateDto(
        Long ownerId,
        Long addressId,
        String name
) {
}