package com.brandwatch.weatherapp.web;

import com.brandwatch.weatherapp.data.entity.AverageReport;
import com.brandwatch.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    @GetMapping("/weather/{city}")
    // Gets and stores current weather data for given city
    public ResponseEntity<Double> getCurrentData(@PathVariable String city) {
        return ResponseEntity.ok(this.weatherService.getCurrentData(city));
    }
    @PostMapping("/weather/{city}/delete")
    // Deletes all stored data for given city
    public ResponseEntity<Void> deleteData(@PathVariable String city) {
        this.weatherService.deleteAll(city);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/weather/{city}/average")
    // Calculates average data for given city between those times
    // Also stores this report for the given user
    public ResponseEntity<AverageReport> calculateAverages(@PathVariable String city, @RequestParam LocalDate from, @RequestParam LocalDate to, @RequestParam String userId) {
        return ResponseEntity.ok(this.weatherService.calculateAverage(city, from, to, userId));
    }

    @GetMapping("/report")
    // Gets all available reports for the given user
    public ResponseEntity<List<AverageReport>> getReports(@RequestParam String city, @RequestParam String user) {
        return ResponseEntity.ok(this.weatherService.getReports(city, user));
    }
}
