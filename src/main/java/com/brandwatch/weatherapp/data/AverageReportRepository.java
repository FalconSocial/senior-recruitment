package com.brandwatch.weatherapp.data;

import com.brandwatch.weatherapp.data.entity.AverageReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AverageReportRepository extends JpaRepository<AverageReport, UUID> {

    List<AverageReport> findByCity(String city);
}
