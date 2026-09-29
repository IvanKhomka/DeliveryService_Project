package com.example.deliveryservice_project.dto;

import java.math.BigDecimal;

public record DishCreateDto(
        Long restaurantId,
        String name,
        BigDecimal price,
        String description
) {
}