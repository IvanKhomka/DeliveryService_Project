package com.example.deliveryservice_project.dto;

import java.util.List;

public record OrderCreateDto(
        Long clientId,
        Long restaurantId,
        Long deliveryAddressId,
        List<OrderItemCreateDto> items
) {
}