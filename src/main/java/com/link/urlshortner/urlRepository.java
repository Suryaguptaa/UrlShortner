package com.link.urlshortner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface urlRepository extends JpaRepository<UrlMapping_entity, Long> {

}
