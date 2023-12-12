package com.brandwatch.weatherapp;

import com.brandwatch.weatherapp.configuration.WeatherAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(WeatherAppConfiguration.class)
@EnableTransactionManagement
public class WeatherApplication {

    protected WeatherApplication() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}

