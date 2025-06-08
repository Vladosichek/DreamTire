package com.example.demo.service;

import com.example.demo.dto.StationStatRequestDTO;
import com.example.demo.dto.StationStatResponseDTO;
import com.example.demo.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StatisticsService {

    private final SessionRepository sessionRepository;

    public StatisticsService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public StationStatResponseDTO getStatistics(StationStatRequestDTO request) {
        List<Map<String, Object>> rawData = sessionRepository.findStatData(
                request.getStartDate(),
                request.getEndDate(),
                request.getStatType().name()
        );
        Set<String> stationNames = new TreeSet<>();
        Set<LocalDate> dates = new TreeSet<>();
        Map<LocalDate, Map<String, Number>> data = new TreeMap<>();
        Map<String, Number> totalPerStation = new HashMap<>();
        for (Map<String, Object> row : rawData) {
            LocalDate date = (LocalDate) row.get("date");
            String station = (String) row.get("station");
            Number value = (Number) row.get("value");
            dates.add(date);
            stationNames.add(station);
            data.computeIfAbsent(date, d -> new HashMap<>()).put(station, value);
            totalPerStation.put(
                    station,
                    totalPerStation.getOrDefault(station, 0.0).doubleValue() + value.doubleValue()
            );
        }
        for (LocalDate date : dates) {
            data.computeIfAbsent(date, d -> new HashMap<>());
            for (String station : stationNames) {
                data.get(date).putIfAbsent(station, 0);
            }
        }
        StationStatResponseDTO response = new StationStatResponseDTO();
        response.setDates(new ArrayList<>(dates));
        response.setStationNames(new ArrayList<>(stationNames));
        response.setData(data);
        response.setTotalPerStation(totalPerStation);
        return response;
    }
}

