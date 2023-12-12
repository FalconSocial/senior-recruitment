package com.brandwatch.weatherapp.service;

import com.brandwatch.weatherapp.data.AverageReportRepository;
import com.brandwatch.weatherapp.data.WeatherRepository;
import com.brandwatch.weatherapp.data.entity.AverageReport;
import com.brandwatch.weatherapp.data.entity.WeatherSample;
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

@SpringBootTest
public class WeatherServiceIT {

    
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private AverageReportRepository averageReportRepository;

    @BeforeEach
    @AfterEach
    public void clearDb() {
        this.weatherRepository.deleteAll();
        this.weatherRepository.flush();

        this.averageReportRepository.deleteAll();
        this.averageReportRepository.flush();
    }

    @Test
    @SneakyThrows
    public void testGetAndStoreData() {
        // -- ARRANGE

        // -- ACT
        double avg1 = this.weatherService.getCurrentData("Budapest");

        // -- ASSERT
        List<WeatherSample> samples = this.weatherRepository.findAll();
        WeatherSample weatherSample = samples.get(0);
        assertEquals(avg1, weatherSample.getSample());
    }

    @Test
    @SneakyThrows
    public void testAverageCalculation() {
        // -- ARRANGE
        this.weatherRepository.save(new WeatherSample(null, Instant.now(), "Budapest", 10.0));
        this.weatherRepository.save(new WeatherSample(null, Instant.now().plusSeconds(TimeUnit.DAYS.toSeconds(2)), "Budapest", 20.0));
        this.weatherRepository.save(new WeatherSample(null, Instant.now().plusSeconds(TimeUnit.DAYS.toSeconds(5)), "Budapest", 30.0));

        // -- ACT
        AverageReport report = this.weatherService.calculateAverage("Budapest", LocalDate.now().minusDays(1), LocalDate.now().plusDays(3), "userId");

        // -- ASSERT
        assertEquals(15.0, report.getAverage());

    }
}
