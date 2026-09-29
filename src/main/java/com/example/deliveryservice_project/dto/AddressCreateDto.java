package com.example.deliveryservice_project.dto;

public record AddressCreateDto(
        Long userId,
        String city,
        String street,
        String building,
        String apartment,
        Double latitude,
        Double longitude
) {
}