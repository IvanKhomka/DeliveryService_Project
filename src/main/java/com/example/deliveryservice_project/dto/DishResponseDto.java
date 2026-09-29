package com.example.deliveryservice_project.dto;

import java.math.BigDecimal;

public record DishResponseDto(
        Long id,
        Long restaurantId,
        String name,
        BigDecimal price,
        String description
) {
}