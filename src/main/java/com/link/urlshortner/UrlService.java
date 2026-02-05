package com.link.urlshortner;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService {


    private final UrlRepository urlRepository;

    private static final String BASE62_ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final int BASE = 62;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String encode(long input) {
        if (input <= 0) {
            throw new IllegalArgumentException("Input must be greater than 0");
        }

        StringBuilder shortCode = new StringBuilder();

        while (input > 0) {
            int remainder = (int) (input % BASE);
            shortCode.append(BASE62_ALPHABET.charAt(remainder));
            input = input / BASE;
        }

        return shortCode.reverse().toString();
    }

        public String shortenUrl(String originalUrl){

        UrlMapping_entity urlMapping = new UrlMapping_entity();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setCreatedDate(LocalDateTime.now());

        urlMapping = urlRepository.save(urlMapping);

        String shortCode = encode(urlMapping.getId());

        urlMapping.setShortCode(shortCode);

        urlRepository.save(urlMapping);

        return shortCode;
        }

    }

