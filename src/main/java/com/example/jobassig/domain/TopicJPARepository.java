package com.example.jobassig.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicJPARepository extends JpaRepository<Topic,Long> {
    Optional<Topic> findByNameIgnoreCase(String name);
}
