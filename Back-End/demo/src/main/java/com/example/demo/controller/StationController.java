package com.example.demo.controller;

import com.example.demo.dto.StationRequestDTO;
import com.example.demo.dto.StationResponseDTO;
import com.example.demo.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public List<StationResponseDTO> getAllStations() {
        return stationService.getAllStations();
    }

    @GetMapping("/search")
    public List<StationResponseDTO> getByName(@RequestParam String name) {
        return stationService.getStationsByName(name);
    }

    @PostMapping
    public ResponseEntity<StationResponseDTO> create(@RequestBody StationRequestDTO dto) {
        return stationService.createStation(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationResponseDTO> update(@PathVariable Long id, @RequestBody StationRequestDTO dto) {
        return stationService.updateStation(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean success = stationService.deleteStation(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}

