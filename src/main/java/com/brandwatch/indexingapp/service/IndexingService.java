package com.brandwatch.indexingapp.service;

import com.brandwatch.indexingapp.configuration.IndexingAppConfiguration;
import com.brandwatch.indexingapp.data.ContentRepository;
import com.brandwatch.indexingapp.data.QueryRepository;
import com.brandwatch.indexingapp.data.entity.Content;
import com.brandwatch.indexingapp.data.entity.Query;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class IndexingService {

    @Getter
    private final IndexingAppConfiguration configuration;

    private final ContentRepository contentRepository;

    private final QueryRepository queryRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void indexAndSendNotifications() {
        try {
            List<Content> contents = this.loadNewContentsForNetwork();
            contents.forEach(this::saveContentAndSendNotifications);
        } catch (Throwable t) {
            log.error("Indexing failed with error: ", t);
        }
    }

    public List<Content> getContents() {
        return this.contentRepository.findByNetwork(this.getNetwork());
    }

    private void saveContentAndSendNotifications(Content content) {
        this.contentRepository.saveAndFlush(content);

        List<Query> matchingQueries = this.queryRepository.findByNetwork(this.getNetwork()).stream()
                .filter(query -> content.getContent().toLowerCase().contains(query.getQueryText()))
                .collect(Collectors.toList());

        matchingQueries.stream()
                .map(query -> new MatchedQueryEventDTO(query.getId(), this.getNetwork(), content.getContent()))
                .forEach(event -> {
                    try {
                        this.kafkaTemplate.send(this.configuration.topic(), this.objectMapper.writeValueAsString(event));
                    } catch (JsonProcessingException e) {
                        log.error("Failed to serialize event: " + event, e);
                        throw new RuntimeException(e);
                    }
                });
    }

    public abstract String getNetwork();

    protected abstract List<Content> loadNewContentsForNetwork() throws Exception;
}
