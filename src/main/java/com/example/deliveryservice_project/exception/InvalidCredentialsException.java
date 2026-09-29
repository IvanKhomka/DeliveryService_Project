package com.example.deliveryservice_project.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Неверный email или пароль");
    }
}