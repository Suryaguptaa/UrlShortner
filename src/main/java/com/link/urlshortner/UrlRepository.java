package com.link.urlshortner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlMapping_entity, Long> {
    Optional<UrlMapping_entity> findByShortCode(String shortCode);
}
