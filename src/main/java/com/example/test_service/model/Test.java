package com.example.test_service.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TestStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Test() {
    }

    public Test(Long id, String title, String description, TestStatus status,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TestBuilder builder() {
        return new TestBuilder();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Builder class
    public static class TestBuilder {
        private Long id;
        private String title;
        private String description;
        private TestStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        TestBuilder() {
        }

        public TestBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TestBuilder status(TestStatus status) {
            this.status = status;
            return this;
        }

        public TestBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TestBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Test build() {
            return new Test(id, title, description, status, createdAt, updatedAt);
        }
    }
}