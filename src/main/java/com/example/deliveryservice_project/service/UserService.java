package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.UserCreateDto;
import com.example.deliveryservice_project.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto createUser(UserCreateDto dto);
}