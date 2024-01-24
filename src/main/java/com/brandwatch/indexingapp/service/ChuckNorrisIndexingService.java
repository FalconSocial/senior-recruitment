package com.brandwatch.indexingapp.service;

import com.brandwatch.indexingapp.configuration.IndexingAppConfiguration;
import com.brandwatch.indexingapp.data.ContentRepository;
import com.brandwatch.indexingapp.data.QueryRepository;
import com.brandwatch.indexingapp.data.entity.Content;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ChuckNorrisIndexingService extends IndexingService {

    private final RestTemplate restTemplate = new RestTemplate();
    public ChuckNorrisIndexingService(IndexingAppConfiguration configuration, ContentRepository contentRepository, QueryRepository queryRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        super(configuration, contentRepository, queryRepository, kafkaTemplate, objectMapper);
    }

    @Override
    public String getNetwork() {
        return "chucknorris";
    }

    @Override
    protected List<Content> loadNewContentsForNetwork() throws URISyntaxException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("X-Api-Key", this.getConfiguration().apiKey());
        RequestEntity request = new RequestEntity<>(requestHeaders, HttpMethod.GET, new URI("https://api.api-ninjas.com/v1/chucknorris"));
        ResponseEntity<Map> response = this.restTemplate.exchange(request, Map.class);
        String joke = String.valueOf(response.getBody().get("joke"));
        Content content = new Content(null, Instant.now(), this.getNetwork(), joke);
        return Collections.singletonList(content);
    }
}
