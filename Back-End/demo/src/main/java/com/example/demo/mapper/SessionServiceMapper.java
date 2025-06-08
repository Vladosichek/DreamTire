package com.example.demo.mapper;

import com.example.demo.dto.SessionServiceRequestDTO;
import com.example.demo.dto.SessionServiceResponseDTO;
import com.example.demo.entity.SessionServices;
import com.example.demo.entity.Session;
import com.example.demo.entity.Service;

public class SessionServiceMapper {

    public static SessionServices toEntity(SessionServiceRequestDTO dto, Session session, Service service) {
        return new SessionServices(session, service, dto.getQuantity());
    }

    public static SessionServiceResponseDTO toResponseDTO(SessionServices sessionServices) {
        return new SessionServiceResponseDTO(
                sessionServices.getId(),
                sessionServices.getSession().getId(),
                sessionServices.getService().getId(),
                sessionServices.getQuantity()
        );
    }

    public static void updateFromDTO(SessionServices sessionServices, SessionServiceRequestDTO dto, Session session, Service service) {
        sessionServices.setSession(session);
        sessionServices.setService(service);
        sessionServices.setQuantity(dto.getQuantity());
    }
}