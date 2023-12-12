package com.brandwatch.indexingapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("indexing-app")
public record IndexingAppConfiguration(String apiKey, String topic) {
}
