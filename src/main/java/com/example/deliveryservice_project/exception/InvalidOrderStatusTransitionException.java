package com.example.deliveryservice_project.exception;

import com.example.deliveryservice_project.order.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException(OrderStatus from, OrderStatus to) {
        super("Невозможно перевести заказ из статуса " + from + " в " + to);
    }
}