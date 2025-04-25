package com.vitira.itreasury.service;

import com.vitira.itreasury.dto.AuthenticationRequest;
import com.vitira.itreasury.dto.AuthenticationResponse;
import com.vitira.itreasury.dto.RegisterRequest;
import com.vitira.itreasury.entity.Role;
import com.vitira.itreasury.entity.Token;
import com.vitira.itreasury.entity.UserEntity;
import com.vitira.itreasury.repository.RoleRepository;
import com.vitira.itreasury.repository.TokenRepository;
import com.vitira.itreasury.repository.UserRepository;
import com.vitira.itreasury.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var userRole = roleRepository.findByName(Role.USER)
                .orElseThrow(() -> new IllegalStateException("Role " + Role.USER + " was not initialized."));

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        var user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .customerCode(request.getCustomerCode())
                .roles(List.of(userRole))
                .accountLocked(false)
                .enabled(true)
                .build();

        // Save user to database
        userRepository.save(user);

        String jwtToken = generateAndSaveJwtToken(user);

        // Return token in response
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String generateAndSaveJwtToken(UserEntity user) {
        var jwtToken = jwtService.generateToken(user);
        var token = Token.builder()
                .token(jwtToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(jwtService.getJwtExpiration()))
                .user(user)
                .build();

        tokenRepository.save(token);

        return jwtToken;
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // First verify the customer code
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!user.getCustomerCode().equals(request.getCustomerCode())) {
            throw new BadCredentialsException("Invalid customer code");
        }

        // Then authenticate with Spring Security
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // If no exception thrown, authentication was successful
        var claims = new HashMap<String, Object>();
        user = ((UserEntity)auth.getPrincipal());
        claims.put("email", user.getEmail());
        claims.put("customerCode", user.getCustomerCode());
        
        // Generate JWT token
        var jwtToken = jwtService.generateToken(claims, user);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public void activateAccount(String token) {
        var savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        var user = userRepository.findById(savedToken.getId())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

} 