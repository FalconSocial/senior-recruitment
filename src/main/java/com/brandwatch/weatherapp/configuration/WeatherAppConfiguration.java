package com.brandwatch.weatherapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("weather-app")
public record WeatherAppConfiguration(String apiKey, String updateTopic) {
}
