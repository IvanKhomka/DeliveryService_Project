package com.example.deliveryservice_project.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Заказ с id=" + id + " не найден");
    }
}