package com.example.demo.mapper;

import com.example.demo.dto.CarRequestDTO;
import com.example.demo.dto.CarResponseDTO;
import com.example.demo.entity.Car;
import com.example.demo.entity.User;

public class CarMapper {

    public static Car toEntity(CarRequestDTO dto, User user) {
        return new Car(user, dto.getNumber(), dto.getMake(), dto.getModel());
    }

    public static CarResponseDTO toResponseDTO(Car car) {
        return new CarResponseDTO(
                car.getId(),
                car.getUser().getId(),
                car.getNumber(),
                car.getMake(),
                car.getModel(),
                car.getUser().getName()
        );
    }

    public static void updateFromDTO(Car car, CarRequestDTO dto, User user) {
        car.setUser(user);
        car.setNumber(dto.getNumber());
        car.setMake(dto.getMake());
        car.setModel(dto.getModel());
    }
}