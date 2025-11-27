package com.example.jobassig.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(length = 10000)
    private String content;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private boolean published;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name="announcement_topics",
            joinColumns = @JoinColumn(name="announcement_id"),
            inverseJoinColumns=@JoinColumn(name="topic_id")
    )
    private Set<Topic> topic = new HashSet<>();

    protected Announcement() {}

    public Announcement(Long id, String title, String content, LocalDateTime startDate, LocalDateTime endDate, boolean published, User owner, Set<Topic> topic) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.published = published;
        this.owner = owner;
        this.topic = topic;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Topic> getTopic() {
        return topic;
    }

    public void setTopic(Set<Topic> topic) {
        this.topic = topic;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private boolean published;
        private User owner;
        private Set<Topic> topic = new HashSet<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }
        public Builder published(boolean published) {
            this.published = published;
            return this;
        }

        public Builder owner(User owner) {
            this.owner = owner;
            return this;
        }
        public Builder topic(Set<Topic> topic) {
            this.topic = topic;
            return this;
        }
        public Announcement build() {
            return new Announcement(id, title, content, startDate, endDate, published, owner, topic);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Announcement)) return false;
        Announcement other = (Announcement) o;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() {
        return 31; // constant is recommended by Hibernate
    }
}
