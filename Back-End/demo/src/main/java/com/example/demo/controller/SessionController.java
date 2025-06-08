package com.example.demo.controller;

import com.example.demo.dto.SessionRequestDTO;
import com.example.demo.dto.SessionResponseDTO;
import com.example.demo.entity.Station;
import com.example.demo.entity.Status;
import com.example.demo.repository.StationRepository;
import com.example.demo.service.SessionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final StationRepository stationRepository;

    public SessionController(SessionService sessionService, StationRepository stationRepository) {
        this.sessionService = sessionService;
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public List<SessionResponseDTO> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/master/{id}")
    public List<SessionResponseDTO> getAllSessionsByMaster(@PathVariable Long id) {
        return sessionService.getByMaster(id);
    }

    @GetMapping("/customer/{id}")
    public List<SessionResponseDTO> getAllSessionsByCustomer(@PathVariable Long id) {
        return sessionService.getByCustomer(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SessionResponseDTO> createSession(@RequestBody SessionRequestDTO dto) {
        return sessionService.createSession(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(@PathVariable Long id,
                                                            @RequestBody SessionRequestDTO dto) {
        return sessionService.updateSession(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        return sessionService.deleteSession(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-date-and-station")
    public ResponseEntity<List<SessionResponseDTO>> getByDateAndStation(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("stationName") String stationName) {

        Optional<Station> stationOpt = stationRepository.findByName(stationName);
        if (stationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<SessionResponseDTO> sessions = sessionService.getByDateAndStation(date, stationOpt.get().getId());
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllStatuses() {
        return ResponseEntity.ok(Arrays.stream(Status.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }
}

