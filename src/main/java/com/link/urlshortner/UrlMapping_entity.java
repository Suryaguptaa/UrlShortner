package com.link.urlshortner;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UrlMapping_entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true)
    private String shortCode;

    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
