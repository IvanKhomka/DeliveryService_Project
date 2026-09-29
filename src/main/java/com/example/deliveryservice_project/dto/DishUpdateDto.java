package com.example.deliveryservice_project.dto;

import java.math.BigDecimal;

public record DishUpdateDto(
        String name,
        BigDecimal price,
        String description
) {
}