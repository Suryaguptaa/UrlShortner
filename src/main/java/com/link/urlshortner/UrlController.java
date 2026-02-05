package com.link.urlshortner;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
public class UrlController {

    private final UrlService urlService;
    private final UrlRepository urlRepository;

    public UrlController(UrlService urlService, UrlRepository urlRepository) {
        this.urlService = urlService;
        this.urlRepository = urlRepository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRequest request){

        String shortCode = urlService.shortenUrl(request.getOriginalUrl());
        return ResponseEntity.ok(shortCode);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){

        UrlMapping_entity urlMapping = urlRepository
                .findByShortCode(shortCode)
                .orElseThrow(()-> new RuntimeException("Short Url not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlMapping.getOriginalUrl()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }
}
