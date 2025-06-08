package com.example.demo.mapper;

import com.example.demo.dto.ServiceRequestDTO;
import com.example.demo.dto.ServiceResponseDTO;
import com.example.demo.entity.Service;
import com.example.demo.entity.Station;

public class ServiceMapper {

    public static Service toEntity(ServiceRequestDTO dto, Station station) {
        return new Service(dto.getName(), dto.getDescription(), station, dto.getDuration(), dto.getCost());
    }

    public static ServiceResponseDTO toResponseDTO(Service service) {
        return new ServiceResponseDTO(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getStation().getId(),
                service.getDuration(),
                service.getCost(),
                service.getStation().getName()
        );
    }

    public static void updateFromDTO(Service service, ServiceRequestDTO dto, Station station) {
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setStation(station);
        service.setDuration(dto.getDuration());
        service.setCost(dto.getCost());
    }
}