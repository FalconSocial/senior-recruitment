package com.brandwatch.indexingapp.service;

import com.brandwatch.indexingapp.data.ContentRepository;
import com.brandwatch.indexingapp.data.entity.Query;
import com.brandwatch.indexingapp.data.entity.Content;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DadJokeServiceIT {

    @Autowired
    private DadJokeIndexingService service;

    @Autowired
    private ContentRepository contentRepository;

    @BeforeEach
    @AfterEach
    public void clearDb() {
        this.contentRepository.deleteAll();
        this.contentRepository.flush();
    }

    @Test
    @SneakyThrows
    public void testGetAndStoreData() {
        // -- ARRANGE

        // -- ACT
        this.service.indexAndSendNotifications();

        // -- ASSERT
        assertTrue(this.contentRepository.findAll().size() > 0);
    }
}
