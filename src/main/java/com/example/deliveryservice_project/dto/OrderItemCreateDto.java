package com.example.deliveryservice_project.dto;

public record OrderItemCreateDto(
        Long dishId,
        Integer quantity
) {
}