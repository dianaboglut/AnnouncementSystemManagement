package com.example.jobassig.service;

import com.example.jobassig.domain.*;
import com.example.jobassig.domain.UserJPARepository;
import com.example.jobassig.dto.AuthResponse;
import com.example.jobassig.dto.LoginRequest;
import com.example.jobassig.dto.RegisterRequest;
import com.example.jobassig.security.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServices {
    private final UserJPARepository users;
    private final PasswordEncoder encoder;
    private final JWTService jwt;

    public AuthServices(UserJPARepository users, PasswordEncoder encoder, JWTService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest req) {
        if (users.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        User u = User.builder()
                .username(req.getUsername())
                .passwordHash(encoder.encode(req.getPassword()))
                .roles(Set.of(Role.USER))
                .build();
        users.save(u);

        String token = jwt.generateToken(u.getUsername(), u.getRoles());
        return new AuthResponse(token, u.getUsername(),
                u.getRoles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet()));
    }

    public AuthResponse login(LoginRequest req) {
        User u = users.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Bad credentials"));

        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Bad credentials");
        }

        String token = jwt.generateToken(u.getUsername(), u.getRoles());
        return new AuthResponse(token, u.getUsername(),
                u.getRoles().stream().map(Enum::name).collect(java.util.stream.Collectors.toSet()));
    }
}
