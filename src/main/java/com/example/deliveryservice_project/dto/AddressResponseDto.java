package com.example.deliveryservice_project.dto;

public record AddressResponseDto(
        Long id,
        Long userId,
        String city,
        String street,
        String building,
        String apartment,
        Double latitude,
        Double longitude
) {
}