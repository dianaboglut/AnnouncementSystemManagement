package com.example.jobassig.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnnouncementJPARepository extends JpaRepository<Announcement,Long> {
    List<Announcement> findByOwnerId(Long ownerId);

    List<Announcement> findByPublishedTrueAndStartDateBeforeAndEndDateAfter(
            LocalDateTime now1, LocalDateTime now2
    );
}
