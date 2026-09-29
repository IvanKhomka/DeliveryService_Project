package com.example.deliveryservice_project.exception;

public class UserNotCourierException extends RuntimeException {
    public UserNotCourierException(Long userId) {
        super("Пользователь с id=" + userId + " не является курьером (role != COURIER)");
    }
}