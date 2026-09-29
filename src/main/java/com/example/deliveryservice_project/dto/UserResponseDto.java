package com.example.deliveryservice_project.dto;

import com.example.deliveryservice_project.roles.UserRole;

public record UserResponseDto(
        Long id,
        String email,
        String phone,
        UserRole role
) {
}