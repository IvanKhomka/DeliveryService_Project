package com.example.deliveryservice_project.dto;

import com.example.deliveryservice_project.order.OrderStatus;

public record OrderStatusUpdateDto(
        OrderStatus status
) {
}