package com.example.deliveryservice_project.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(Long id) {
        super("Блюдо с id=" + id + " не найдено");
    }
}