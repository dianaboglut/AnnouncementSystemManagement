package com.example.jobassig.service;

import com.example.jobassig.domain.*;
import com.example.jobassig.dto.AuthResponse;
import com.example.jobassig.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserJPARepository userJPARepository;
    private final AnnouncementJPARepository announcementJPARepository;

    public UserService(UserJPARepository userJPARepository, AnnouncementJPARepository announcementJPARepository) {
        this.userJPARepository = userJPARepository;
        this.announcementJPARepository = announcementJPARepository;
    }

    public List<UserResponseDto> getAllUsers() {
        return userJPARepository.findAll()
                .stream()
                .map(u -> new UserResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getRoles().stream().map(r -> r.name()).collect(java.util.stream.Collectors.toSet())
                ))
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        return userJPARepository.findById(id)
                .map(u -> new UserResponseDto(
                        u.getId(),
                        u.getUsername(),
                        u.getRoles().stream().map(r -> r.name()).collect(java.util.stream.Collectors.toSet())
                ))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDto updateRoles(Long id, Set<String> roles) {
        User u = userJPARepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Role> updatedRoles = roles.stream()
                .map(Role::valueOf)
                .collect(java.util.stream.Collectors.toSet());

        u.setRoles(updatedRoles);
        userJPARepository.save(u);

        Set<String> dtoRoles = updatedRoles.stream()
                .map(Enum::name)
                .collect(java.util.stream.Collectors.toSet());

        return new UserResponseDto(
                u.getId(),
                u.getUsername(),
                dtoRoles
        );
    }

    public void deleteUser(Long id) {
        User u = userJPARepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        List<Announcement> announcementList =announcementJPARepository.findByOwnerId(id);
        announcementList.forEach(announcementJPARepository::delete);

        userJPARepository.delete(u);
    }
}
