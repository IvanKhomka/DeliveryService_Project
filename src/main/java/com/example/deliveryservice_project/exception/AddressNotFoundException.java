package com.example.deliveryservice_project.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {
        super("Адрес с id=" + id + " не найден");
    }
}