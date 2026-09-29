package com.example.deliveryservice_project.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long dishId,
        String dishName,
        Integer quantity,
        BigDecimal priceAtPurchase
) {
}