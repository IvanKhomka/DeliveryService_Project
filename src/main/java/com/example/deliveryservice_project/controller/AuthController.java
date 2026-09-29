package com.example.deliveryservice_project.controller;

import com.example.deliveryservice_project.dto.LoginRequestDto;
import com.example.deliveryservice_project.dto.LoginResponseDto;
import com.example.deliveryservice_project.exception.InvalidCredentialsException;
import com.example.deliveryservice_project.model.User;
import com.example.deliveryservice_project.repository.UserRepository;
import com.example.deliveryservice_project.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(dto.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponseDto(token, user.getId(), user.getEmail(), user.getRole());
    }
}