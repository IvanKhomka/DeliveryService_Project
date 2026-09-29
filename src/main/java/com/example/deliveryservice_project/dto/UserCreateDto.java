package com.example.deliveryservice_project.dto;

import com.example.deliveryservice_project.roles.UserRole;

public record UserCreateDto(
        String email,
        String password,
        String phone,
        UserRole role
) {
}