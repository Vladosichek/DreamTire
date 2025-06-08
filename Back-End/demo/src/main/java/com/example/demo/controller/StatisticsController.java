package com.example.demo.controller;

import com.example.demo.dto.StationStatRequestDTO;
import com.example.demo.dto.StationStatResponseDTO;
import com.example.demo.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping
    public ResponseEntity<StationStatResponseDTO> getStationStats(
            @RequestBody StationStatRequestDTO request
    ) {
        StationStatResponseDTO response = statisticsService.getStatistics(request);
        return ResponseEntity.ok(response);
    }
}

