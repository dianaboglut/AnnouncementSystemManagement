package com.example.jobassig.controller;

import com.example.jobassig.dto.AnnouncementResponseDto;
import com.example.jobassig.service.AnnouncementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    private final AnnouncementService announcementService;

    public PublicController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/billboard")
    public List<AnnouncementResponseDto> billboard(){
        return announcementService.publicBillboard();
    }
}
