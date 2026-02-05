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

    @Column(nullable = false, unique = true)
    private String shortCode;

    @Column(nullable = false)
    private LocalDateTime createdDate;


    public void setShortCode(String shortCode) {
    }

    public long getId() {
    }

    public void setCreatedDate(LocalDateTime now) {
    }

    public void setOriginalUrl(String originalUrl) {
    }
}
