package com.example.jobassig.controller;

import com.example.jobassig.dto.AnnouncementCreateUpdateDto;
import com.example.jobassig.dto.AnnouncementResponseDto;
import com.example.jobassig.service.AnnouncementService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    public AnnouncementResponseDto create(@RequestBody AnnouncementCreateUpdateDto req, Authentication auth){
        return announcementService.create(req,auth);
    }

    @GetMapping("/{id}")
    public AnnouncementResponseDto getById(@PathVariable long id, Authentication auth){
        return announcementService.getById(id, auth);
    }

    @Secured("ROLE_USER")
    @GetMapping("/mine")
    public List<AnnouncementResponseDto> listMine(Authentication auth) {
        return announcementService.listMine(auth);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<AnnouncementResponseDto> listAll() {
        return announcementService.listAll();
    }

    @PutMapping("/{id}")
    public AnnouncementResponseDto update(@PathVariable Long id, @RequestBody AnnouncementCreateUpdateDto dto, Authentication auth) {
        return announcementService.update(id, dto, auth);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        announcementService.delete(id, auth);
    }
}
