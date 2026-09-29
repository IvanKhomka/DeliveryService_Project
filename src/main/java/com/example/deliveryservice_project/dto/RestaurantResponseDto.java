package com.example.deliveryservice_project.dto;

public record RestaurantResponseDto(
        Long id,
        String name,
        Long ownerId,
        Long addressId
) {
}