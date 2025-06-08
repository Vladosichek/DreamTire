package com.example.demo.service;

import com.example.demo.dto.ServiceRequestDTO;
import com.example.demo.dto.ServiceResponseDTO;
import com.example.demo.entity.Service;
import com.example.demo.entity.Station;
import com.example.demo.mapper.ServiceMapper;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.StationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final StationRepository stationRepository;

    public ServiceService(ServiceRepository serviceRepository, StationRepository stationRepository) {
        this.serviceRepository = serviceRepository;
        this.stationRepository = stationRepository;
    }

    public Optional<ServiceResponseDTO> createService(ServiceRequestDTO dto) {
        Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
        if (stationOpt.isEmpty()) {
            return Optional.empty();
        }
        Service service = ServiceMapper.toEntity(dto, stationOpt.get());
        Service saved = serviceRepository.save(service);
        return Optional.of(ServiceMapper.toResponseDTO(saved));
    }

    public Optional<ServiceResponseDTO> getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(ServiceMapper::toResponseDTO);
    }

    public List<ServiceResponseDTO> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(ServiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ServiceResponseDTO> getServicesByStationId(Long stationId) {
        return serviceRepository.findAllByStationId(stationId)
                .stream()
                .map(ServiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<ServiceResponseDTO> updateService(Long id, ServiceRequestDTO dto) {
        Optional<Service> serviceOpt = serviceRepository.findById(id);
        Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
        if (serviceOpt.isEmpty() || stationOpt.isEmpty()) {
            return Optional.empty();
        }
        Service service = serviceOpt.get();
        ServiceMapper.updateFromDTO(service, dto, stationOpt.get());
        Service updated = serviceRepository.save(service);
        return Optional.of(ServiceMapper.toResponseDTO(updated));
    }

    public boolean deleteService(Long id) {
        if (serviceRepository.existsById(id)) {
            serviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
