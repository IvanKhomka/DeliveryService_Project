package com.example.deliveryservice_project.dto;

import com.example.deliveryservice_project.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        Long clientId,
        Long restaurantId,
        Long courierId,
        Long deliveryAddressId,
        OrderStatus status,
        BigDecimal totalPrice,
        List<OrderItemResponseDto> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deliveredAt
) {
}