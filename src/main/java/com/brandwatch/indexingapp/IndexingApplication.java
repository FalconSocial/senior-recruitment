package com.brandwatch.indexingapp;

import com.brandwatch.indexingapp.configuration.IndexingAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(IndexingAppConfiguration.class)
@EnableTransactionManagement
public class IndexingApplication {

    protected IndexingApplication() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(IndexingApplication.class, args);
    }

}

