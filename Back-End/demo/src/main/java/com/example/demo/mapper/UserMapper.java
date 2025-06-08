package com.example.demo.mapper;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Station;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto, Station station) {
        return new User(dto.getTelephone(), dto.getEmail(), dto.getName(), dto.getRole(), dto.getPassword(), station);
    }

    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setStationId(user.getStation() != null ? user.getStation().getId() : null);
        dto.setStationName(user.getStation() != null ? user.getStation().getName() : null);
        return dto;
    }

    public static void updateFromDTO(User user, UserRequestDTO dto, Station station) {
        user.setTelephone(dto.getTelephone());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());
        user.setStation(station);
    }
}