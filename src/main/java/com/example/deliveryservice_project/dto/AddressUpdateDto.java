package com.example.deliveryservice_project.dto;

public record AddressUpdateDto(
        String city,
        String street,
        String building,
        String apartment,
        Double latitude,
        Double longitude
) {
}