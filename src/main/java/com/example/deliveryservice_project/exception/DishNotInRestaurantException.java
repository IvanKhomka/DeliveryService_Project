package com.example.deliveryservice_project.exception;

public class DishNotInRestaurantException extends RuntimeException {
    public DishNotInRestaurantException(Long dishId, Long restaurantId) {
        super("Блюдо с id=" + dishId + " не принадлежит ресторану с id=" + restaurantId);
    }
}