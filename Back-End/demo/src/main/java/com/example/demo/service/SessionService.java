package com.example.demo.service;

import com.example.demo.dto.SessionRequestDTO;
import com.example.demo.dto.SessionResponseDTO;
import com.example.demo.entity.*;
import com.example.demo.mapper.SessionMapper;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final StationRepository stationRepository;
    private final SessionServiceRepository sessionServiceRepository;
    private final ServiceRepository serviceRepository;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository,
                          CarRepository carRepository,
                          StationRepository stationRepository,
                          SessionServiceRepository sessionServiceRepository,
                          ServiceRepository serviceRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.stationRepository = stationRepository;
        this.sessionServiceRepository = sessionServiceRepository;
        this.serviceRepository = serviceRepository;
    }

    public Optional<SessionResponseDTO> createSession(SessionRequestDTO dto) {
        Optional<User> customerOpt = userRepository.findById(dto.getCustomerId());
        Optional<Car> carOpt = carRepository.findById(dto.getCarId());
        Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
        Optional<User> masterOpt = Optional.empty();
        if (dto.getMasterId() != null) {
            masterOpt = userRepository.findById(dto.getMasterId());
        }
        if (customerOpt.isEmpty() || carOpt.isEmpty() || stationOpt.isEmpty() || (dto.getMasterId() != null && masterOpt.isEmpty())) {
            return Optional.empty();
        }
        Session session = SessionMapper.toEntity(dto, customerOpt.get(), masterOpt.orElse(null), carOpt.get(), stationOpt.get());
        Session saved = sessionRepository.save(session);
        if (dto.getServices() != null) {
            List<SessionServices> sessionServices = dto.getServices().stream()
                    .map(item -> {
                        Service service = serviceRepository.findById(item.getServiceId())
                                .orElseThrow(() -> new RuntimeException("Service not found"));
                        SessionServices ss = new SessionServices();
                        ss.setSession(saved);
                        ss.setService(service);
                        ss.setQuantity(item.getQuantity());
                        return ss;
                    }).collect(Collectors.toList());
            sessionServiceRepository.saveAll(sessionServices);
        }
        return Optional.of(SessionMapper.toResponseDTO(saved));
    }

    public Optional<SessionResponseDTO> getSessionById(Long id) {
        return sessionRepository.findById(id)
                .map(SessionMapper::toResponseDTO);
    }

    public List<SessionResponseDTO> getAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(SessionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<SessionResponseDTO> updateSession(Long id, SessionRequestDTO dto) {
        Optional<Session> sessionOpt = sessionRepository.findById(id);
        Optional<User> customerOpt = userRepository.findById(dto.getCustomerId());
        Optional<Car> carOpt = carRepository.findById(dto.getCarId());
        Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
        Optional<User> masterOpt = Optional.empty();
        if (dto.getMasterId() != null) {
            masterOpt = userRepository.findById(dto.getMasterId());
        }
        if (sessionOpt.isEmpty() || customerOpt.isEmpty() || carOpt.isEmpty() || stationOpt.isEmpty() || (dto.getMasterId() != null && masterOpt.isEmpty())) {
            return Optional.empty();
        }
        Session session = sessionOpt.get();
        SessionMapper.updateFromDTO(session, dto, customerOpt.get(), masterOpt.orElse(null), carOpt.get(), stationOpt.get());
        Session updated = sessionRepository.save(session);
        if (dto.getServices() != null) {
            sessionServiceRepository.deleteBySessionId(updated.getId());
            List<SessionServices> sessionServices = dto.getServices().stream()
                    .map(item -> {
                        Service service = serviceRepository.findById(item.getServiceId())
                                .orElseThrow(() -> new RuntimeException("Service not found"));
                        SessionServices ss = new SessionServices();
                        ss.setSession(updated);
                        ss.setService(service);
                        ss.setQuantity(item.getQuantity());
                        return ss;
                    }).collect(Collectors.toList());
            sessionServiceRepository.saveAll(sessionServices);
        }
        return Optional.of(SessionMapper.toResponseDTO(updated));
    }


    public boolean deleteSession(Long id) {
        if (sessionRepository.existsById(id)) {
            sessionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<SessionResponseDTO> getByDateAndStation(LocalDate date, Long stationId) {
        return sessionRepository.findByDateAndStationId(date, stationId)
                .stream()
                .map(SessionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<SessionResponseDTO> getByMaster(Long masterId) {
        return sessionRepository.findByMasterId(masterId)
                .stream()
                .map(SessionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<SessionResponseDTO> getByCustomer(Long customerId) {
        return sessionRepository.findByCustomerId(customerId)
                .stream()
                .map(SessionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
