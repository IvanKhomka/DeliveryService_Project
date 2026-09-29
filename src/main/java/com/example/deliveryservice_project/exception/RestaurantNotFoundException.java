package com.example.deliveryservice_project.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long id) {
        super("Ресторан с id=" + id + " не найден");
    }
}