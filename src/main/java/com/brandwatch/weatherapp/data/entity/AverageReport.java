package com.brandwatch.weatherapp.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "average_result")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AverageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate averageFrom;

    private LocalDate averageTo;

    private String city;

    private String userId;

    private double average;
}
