package com.example.demo.mapper;

import com.example.demo.dto.SessionRequestDTO;
import com.example.demo.dto.SessionResponseDTO;
import com.example.demo.dto.SessionServiceItemDTO;
import com.example.demo.entity.Session;
import com.example.demo.entity.User;
import com.example.demo.entity.Car;
import com.example.demo.entity.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SessionMapper {

    public static Session toEntity(SessionRequestDTO dto, User customer, User master, Car car, Station station) {
        Session session = new Session();
        session.setDate(dto.getDate());
        session.setTime(dto.getTime());
        session.setDuration(dto.getDuration());
        session.setCost(dto.getCost());
        session.setDiscount(dto.getDiscount());
        session.setDescription(dto.getDescription());
        session.setCustomer(customer);
        session.setMaster(master);
        session.setCar(car);
        session.setStation(station);
        session.setStatus(dto.getStatus());
        return session;
    }


    public static SessionResponseDTO toResponseDTO(Session session) {
        Long masterIdNumber = session.getMaster() != null
                ? session.getMaster().getId()
                : null;

        List<SessionServiceItemDTO> serviceItems = session.getSessionServices() != null
                ? session.getSessionServices().stream()
                .map(ss -> new SessionServiceItemDTO(
                        ss.getService().getId(),
                        ss.getService().getName(),
                        ss.getQuantity()
                ))
                .collect(Collectors.toList())
                : new ArrayList<>();

        return new SessionResponseDTO(
                session.getId(),
                session.getDate(),
                session.getTime(),
                session.getDuration(),
                session.getCost(),
                session.getDiscount(),
                session.getDescription(),
                session.getStatus().toString(),
                session.getCustomer().getId(),
                masterIdNumber,
                session.getCar().getId(),
                session.getStation().getId(),
                serviceItems
        );
    }


    public static void updateFromDTO(Session session, SessionRequestDTO dto, User customer, User master, Car car, Station station) {
        session.setDate(dto.getDate());
        session.setCustomer(customer);
        session.setMaster(master);
        session.setCar(car);
        session.setTime(dto.getTime());
        session.setDuration(dto.getDuration());
        session.setCost(dto.getCost());
        session.setStation(station);
        session.setDiscount(dto.getDiscount());
        session.setStatus(dto.getStatus());
        session.setDescription(dto.getDescription());
    }
}