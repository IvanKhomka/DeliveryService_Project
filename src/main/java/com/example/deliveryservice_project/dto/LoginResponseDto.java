package com.example.deliveryservice_project.dto;

import com.example.deliveryservice_project.roles.UserRole;

public record LoginResponseDto(String token, Long userId, String email, UserRole role) {
}