package com.example.deliveryservice_project.exception;

public class EmptyOrderException extends RuntimeException {
    public EmptyOrderException() {
        super("Заказ не может быть пустым — добавьте хотя бы одно блюдо");
    }
}