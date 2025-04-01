package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.AuthRequest;
import com.vitira.itreasury.dto.AuthResponse;
import com.vitira.itreasury.dto.RegisterRequest;
import com.vitira.itreasury.entity.Role;
import com.vitira.itreasury.entity.UserEntity;
import com.vitira.itreasury.repository.UserRepository;
import com.vitira.itreasury.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // Create new user entity
        var user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(Role.USER)
                .build();

        // Save user to database
        userRepository.save(user);
        
        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);
        
        // Return token in response
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Authenticate user with Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        // If no exception thrown, authentication was successful
        // Find the user by email
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate JWT token
        var jwtToken = jwtService.generateToken(user);
        
        // Return token in response
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
} 