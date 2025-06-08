package com.example.demo.service;

import com.example.demo.dto.StationRequestDTO;
import com.example.demo.dto.StationResponseDTO;
import com.example.demo.entity.Station;
import com.example.demo.mapper.StationMapper;
import com.example.demo.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Optional<StationResponseDTO> createStation(StationRequestDTO dto) {
        Station station = StationMapper.toEntity(dto);
        Station saved = stationRepository.save(station);
        return Optional.of(StationMapper.toResponseDTO(saved));
    }

    public Optional<StationResponseDTO> getStationById(Long id) {
        return stationRepository.findById(id)
                .map(StationMapper::toResponseDTO);
    }

    public List<StationResponseDTO> getStationsByName(String name) {
        return stationRepository.findAllByName(name)
                .stream()
                .map(StationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<StationResponseDTO> getAllStations() {
        return stationRepository.findAll()
                .stream()
                .map(StationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<StationResponseDTO> updateStation(Long id, StationRequestDTO dto) {
        Optional<Station> stationOpt = stationRepository.findById(id);
        if (stationOpt.isEmpty()) {
            return Optional.empty();
        }
        Station station = stationOpt.get();
        StationMapper.updateFromDTO(station, dto);
        Station updated = stationRepository.save(station);
        return Optional.of(StationMapper.toResponseDTO(updated));
    }

    public boolean deleteStation(Long id) {
        if (stationRepository.existsById(id)) {
            stationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
