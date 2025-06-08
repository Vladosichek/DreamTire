package com.example.demo.service;

import com.example.demo.dto.CarRequestDTO;
import com.example.demo.dto.CarResponseDTO;
import com.example.demo.entity.Car;
import com.example.demo.entity.User;
import com.example.demo.mapper.CarMapper;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public Optional<CarResponseDTO> createCar(CarRequestDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        Car car = CarMapper.toEntity(dto, userOpt.get());
        Car saved = carRepository.save(car);
        return Optional.of(CarMapper.toResponseDTO(saved));
    }

    public Optional<CarResponseDTO> getCarById(Long id) {
        return carRepository.findById(id)
                .map(CarMapper::toResponseDTO);
    }

    public List<CarResponseDTO> getCarsByUserId(Long userId) {
        return carRepository.findByUserId(userId)
                .stream()
                .map(CarMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CarResponseDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<CarResponseDTO> updateCar(Long id, CarRequestDTO dto) {
        Optional<Car> carOpt = carRepository.findById(id);
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (carOpt.isEmpty() || userOpt.isEmpty()) {
            return Optional.empty();
        }
        Car car = carOpt.get();
        CarMapper.updateFromDTO(car, dto, userOpt.get());
        Car updated = carRepository.save(car);
        return Optional.of(CarMapper.toResponseDTO(updated));
    }

    public boolean deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
