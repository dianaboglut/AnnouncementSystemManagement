package com.example.jobassig.controller;

import com.example.jobassig.dto.UserResponseDto;
import com.example.jobassig.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService user;

    public UserController(UserService user) {
        this.user = user;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<UserResponseDto> listUsers() {
        return user.getAllUsers();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return user.getUserById(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/roles")
    public UserResponseDto updateRoles(@PathVariable Long id, @RequestBody UserResponseDto req) {
        UserResponseDto updated = user.updateRoles(id, req.getRoles());
        return updated;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        user.deleteUser(id);
    }
}
