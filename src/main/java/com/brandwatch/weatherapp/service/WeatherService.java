package com.brandwatch.weatherapp.service;

import com.brandwatch.weatherapp.configuration.WeatherAppConfiguration;
import com.brandwatch.weatherapp.data.AverageReportRepository;
import com.brandwatch.weatherapp.data.WeatherRepository;
import com.brandwatch.weatherapp.data.entity.AverageReport;
import com.brandwatch.weatherapp.data.entity.WeatherSample;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final WeatherAppConfiguration weatherAppConfiguration;

    private final WeatherRepository weatherRepository;

    private final AverageReportRepository averageResultRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public double getCurrentData(String city) {
        // Documentation at https://openweathermap.org/current#data
        String apiCall = "https://api.openweathermap.org/data/2.5/weather?unites=metric&q=%s&appid=%s".formatted(city, weatherAppConfiguration.apiKey());
        final JsonNode node = restTemplate.getForObject(apiCall, JsonNode.class);
        JsonNode mainDataNode = node.get("main");
        JsonNode tempNode = mainDataNode.get("temp");
        double temp = tempNode.asDouble();

        this.weatherRepository.save(new WeatherSample(null, Instant.now(), city, temp));
        this.weatherRepository.flush();
        return temp;
    }

    public AverageReport calculateAverage(String city, LocalDate from, LocalDate to, String userId) {
        try {
            Double avg = calculateAverageValue(city, from, to);
            log.info("Calculated average of {} from {} to {} = {}", city, from, to, avg);
            AverageReport report = new AverageReport(null, from, to, city, userId, avg);
            this.averageResultRepository.save(report);
            this.averageResultRepository.flush();
            this.kafkaTemplate.send(weatherAppConfiguration.updateTopic(), objectMapper.writeValueAsString(report));
            return report;
        } catch (Throwable t) {
            log.error("Failed to create report", t);
            throw new RuntimeException(t);
        }
    }

    public void deleteAll(String city) {
        this.weatherRepository.deleteAll(this.weatherRepository.findByCity(city));
        this.averageResultRepository.deleteAll(this.averageResultRepository.findByCity(city));
        this.weatherRepository.flush();
        this.averageResultRepository.flush();
    }

    private Double calculateAverageValue(String city, LocalDate from, LocalDate to) {
        List<Double> collect = this.weatherRepository.findByCity(city)
                .parallelStream()
                .filter(sample -> sample.getSampleTime().isAfter(from.atStartOfDay().toInstant(ZoneOffset.UTC)))
                .filter(sample -> sample.getSampleTime().isBefore(to.atStartOfDay().toInstant(ZoneOffset.UTC)))
                .map(WeatherSample::getSample)
                .collect(Collectors.toList());
        Double sum = collect.stream().reduce((s1, s2) -> s1 + s2).get();
        Double avg = sum / collect.size();
        return avg;
    }

    public List<AverageReport> getReports(String city, String user) {
        List<AverageReport> reports = this.averageResultRepository.findByCity(city);
        if (user != null) {
            reports.removeIf(report -> !report.getUserId().equals(user));
        }
        return reports;
    }
}
