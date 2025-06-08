package com.example.demo.service;

import com.example.demo.dto.SessionServiceItemDTO;
import com.example.demo.dto.SessionServiceRequestDTO;
import com.example.demo.dto.SessionServiceResponseDTO;
import com.example.demo.entity.Session;
import com.example.demo.entity.Service;
import com.example.demo.entity.SessionServices;
import com.example.demo.mapper.SessionServiceMapper;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.SessionServiceRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SessionServiceService {

    private final SessionServiceRepository sessionServiceRepository;
    private final SessionRepository sessionRepository;
    private final ServiceRepository serviceRepository;

    public SessionServiceService(SessionServiceRepository sessionServiceRepository,
                                 SessionRepository sessionRepository,
                                 ServiceRepository serviceRepository) {
        this.sessionServiceRepository = sessionServiceRepository;
        this.sessionRepository = sessionRepository;
        this.serviceRepository = serviceRepository;
    }

    public Optional<SessionServiceResponseDTO> create(SessionServiceRequestDTO dto) {
        Optional<Session> sessionOpt = sessionRepository.findById(dto.getSessionId());
        Optional<Service> serviceOpt = serviceRepository.findById(dto.getServiceId());
        if (sessionOpt.isEmpty() || serviceOpt.isEmpty()) {
            return Optional.empty();
        }
        SessionServices sessionServices = SessionServiceMapper.toEntity(dto, sessionOpt.get(), serviceOpt.get());
        SessionServices saved = sessionServiceRepository.save(sessionServices);
        return Optional.of(SessionServiceMapper.toResponseDTO(saved));
    }

    public Optional<SessionServiceResponseDTO> getById(Long id) {
        return sessionServiceRepository.findById(id)
                .map(SessionServiceMapper::toResponseDTO);
    }

    public List<SessionServiceResponseDTO> getAll() {
        return sessionServiceRepository.findAll()
                .stream()
                .map(SessionServiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<SessionServiceResponseDTO> getAllBySessionId(Long sessionId) {
        return sessionServiceRepository.findAllBySessionId(sessionId)
                .stream()
                .map(SessionServiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<SessionServiceResponseDTO> update(Long id, SessionServiceRequestDTO dto) {
        Optional<SessionServices> existingOpt = sessionServiceRepository.findById(id);
        Optional<Session> sessionOpt = sessionRepository.findById(dto.getSessionId());
        Optional<Service> serviceOpt = serviceRepository.findById(dto.getServiceId());
        if (existingOpt.isEmpty() || sessionOpt.isEmpty() || serviceOpt.isEmpty()) {
            return Optional.empty();
        }
        SessionServices existing = existingOpt.get();
        SessionServiceMapper.updateFromDTO(existing, dto, sessionOpt.get(), serviceOpt.get());
        SessionServices updated = sessionServiceRepository.save(existing);
        return Optional.of(SessionServiceMapper.toResponseDTO(updated));
    }

    public boolean delete(Long id) {
        if (sessionServiceRepository.existsById(id)) {
            sessionServiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateSessionServices(Session session, List<SessionServiceItemDTO> items) {
        sessionServiceRepository.deleteAll(sessionServiceRepository.findAllBySession(session));
        List<SessionServices> newServices = items.stream().map(item -> {
            Service service = serviceRepository.findById(item.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));
            SessionServices ss = new SessionServices();
            ss.setSession(session);
            ss.setService(service);
            ss.setQuantity(item.getQuantity());
            return ss;
        }).collect(Collectors.toList());
        sessionServiceRepository.saveAll(newServices);
    }
}
