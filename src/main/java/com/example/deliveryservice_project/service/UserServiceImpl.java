package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.UserCreateDto;
import com.example.deliveryservice_project.dto.UserResponseDto;
import com.example.deliveryservice_project.exception.EmailAlreadyExistsException;
import com.example.deliveryservice_project.exception.UserNotFoundException;
import com.example.deliveryservice_project.model.User;
import com.example.deliveryservice_project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }
        User user = new User(
                dto.email(),
                passwordEncoder.encode(dto.password()),
                dto.phone(),
                dto.role()
        );
        return toResponseDto(userRepository.save(user));
    }

    private UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getPhone(), user.getRole());
    }
}