package com.example.jobassig.service;

import com.example.jobassig.domain.*;
import com.example.jobassig.dto.AnnouncementCreateUpdateDto;
import com.example.jobassig.dto.AnnouncementResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementJPARepository announcementJPARepository;
    private final UserJPARepository users;
    private final TopicJPARepository topics;

    public AnnouncementService(AnnouncementJPARepository announcementJPARepository, UserJPARepository users, TopicJPARepository topics) {
        this.announcementJPARepository = announcementJPARepository;
        this.users = users;
        this.topics = topics;
    }

    private User currentUser(Authentication auth) {
        return users.findByUsername(auth.getName()).orElseThrow();
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private Set<Topic> resolveTopics(Set<String> topicNames) {
        if (topicNames == null) return Set.of();

        return topicNames.stream()
                .map(name ->
                        topics.findByNameIgnoreCase(name)
                                .orElseGet(() -> topics.save(new Topic(name))))
                .collect(Collectors.toSet());
    }

    public AnnouncementResponseDto create(AnnouncementCreateUpdateDto req, Authentication auth) {
        Announcement a = Announcement.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .published(req.isPublished())
                .owner(currentUser(auth))
                .topic(resolveTopics(req.getTopics()))
                .build();

        announcementJPARepository.save(a);

        return new AnnouncementResponseDto(
                a.getId(),
                a.getTitle(),
                a.getContent(),
                a.getStartDate(),
                a.getEndDate(),
                a.isPublished(),
                a.getOwner().getUsername(),
                a.getTopic().stream().map(Topic::getName).collect(java.util.stream.Collectors.toSet())
        );
    }

    public AnnouncementResponseDto getById(long id, Authentication auth) {
        Announcement a = announcementJPARepository.findById(id).orElseThrow(() -> new RuntimeException("Announcement not found"));

        if (!isAdmin(auth) && !a.getOwner().getUsername().equals(auth.getName())) {
            throw new RuntimeException("Forbidden");
        }

        return new AnnouncementResponseDto(
                a.getId(),
                a.getTitle(),
                a.getContent(),
                a.getStartDate(),
                a.getEndDate(),
                a.isPublished(),
                a.getOwner().getUsername(),
                a.getTopic()
                        .stream()
                        .map(Topic::getName)
                        .collect(Collectors.toSet())
        );
    }

    public List<AnnouncementResponseDto> listMine(Authentication auth) {
        User me = currentUser(auth);
        return announcementJPARepository.findByOwnerId(me.getId())
                .stream()
                .map(a -> new AnnouncementResponseDto(
                        a.getId(),
                        a.getTitle(),
                        a.getContent(),
                        a.getStartDate(),
                        a.getEndDate(),
                        a.isPublished(),
                        a.getOwner().getUsername(),
                        a.getTopic().stream().map(Topic::getName).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    public List<AnnouncementResponseDto> listAll() {
        return announcementJPARepository.findAll()
                .stream()
                .map(a -> new AnnouncementResponseDto(
                        a.getId(),
                        a.getTitle(),
                        a.getContent(),
                        a.getStartDate(),
                        a.getEndDate(),
                        a.isPublished(),
                        a.getOwner().getUsername(),
                        a.getTopic().stream().map(Topic::getName).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    public AnnouncementResponseDto update(Long id, AnnouncementCreateUpdateDto dto, Authentication auth) {
        Announcement a = announcementJPARepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        if (!isAdmin(auth) && !a.getOwner().getUsername().equals(auth.getName())) {
            throw new RuntimeException("Forbidden");
        }

        a.setTitle(dto.getTitle());
        a.setContent(dto.getContent());
        a.setStartDate(dto.getStartDate());
        a.setEndDate(dto.getEndDate());
        a.setPublished(dto.isPublished());
        a.setTopic(resolveTopics(dto.getTopics()));

        announcementJPARepository.save(a);

        return new AnnouncementResponseDto(
                a.getId(),
                a.getTitle(),
                a.getContent(),
                a.getStartDate(),
                a.getEndDate(),
                a.isPublished(),
                a.getOwner().getUsername(),
                a.getTopic().stream().map(Topic::getName).collect(Collectors.toSet())
        );
    }

    public void delete(Long id, Authentication auth) {
        Announcement a = announcementJPARepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Announcement not found"));

        if (!isAdmin(auth) && !a.getOwner().getUsername().equals(auth.getName())) {
            throw new RuntimeException("Forbidden");
        }

        announcementJPARepository.delete(a);
    }

    public List<AnnouncementResponseDto> publicBillboard() {
        LocalDateTime now = LocalDateTime.now();

        return announcementJPARepository
                .findByPublishedTrueAndStartDateBeforeAndEndDateAfter(now, now)
                .stream()
                .map(a -> new AnnouncementResponseDto(
                        a.getId(),
                        a.getTitle(),
                        a.getContent(),
                        a.getStartDate(),
                        a.getEndDate(),
                        a.isPublished(),
                        a.getOwner().getUsername(),
                        a.getTopic().stream().map(Topic::getName).collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }
}
