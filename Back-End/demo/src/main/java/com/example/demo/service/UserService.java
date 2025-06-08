package com.example.demo.service;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.Station;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.StationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final JavaMailSender mailSender;

    public UserService(UserRepository userRepository, StationRepository stationRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
        this.mailSender = mailSender;
    }

    public Optional<UserResponseDTO> createUser(UserRequestDTO dto) {
        Station station = null;
        if (dto.getStationId() != null) {
            Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
            if (stationOpt.isEmpty()) {
                return Optional.empty();
            }
            station = stationOpt.get();
        }
        User user = UserMapper.toEntity(dto, station);
        User saved = userRepository.save(user);
        return Optional.of(UserMapper.toResponseDTO(saved));
    }

    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponseDTO);
    }

    public Optional<UserResponseDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toResponseDTO);
    }

    public List<UserResponseDTO> getUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> getUsersByRole(Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> getUsersByRoleAndStation(Role role, Long stationId) {
        Optional<Station> stationOpt = stationRepository.findById(stationId);
        if (stationOpt.isEmpty()) {
            return List.of();
        }
        return userRepository.findByRoleAndStation(role, stationOpt.get())
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> getUsersByRoleAndStationIsNull(Role role) {
        return userRepository.findByRoleAndStationIsNull(role)
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> updateUser(Long id, UserRequestDTO dto) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        Station station = null;
        if (dto.getStationId() != null) {
            Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
            if (stationOpt.isEmpty()) {
                return Optional.empty();
            }
            station = stationOpt.get();
        }
        User user = userOpt.get();
        UserMapper.updateFromDTO(user, dto, station);
        User updated = userRepository.save(user);
        return Optional.of(UserMapper.toResponseDTO(updated));
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void sendEmailToUser(User user, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Повідомлення від адміністрації DreamTire");
        mail.setText(message);
        mailSender.send(mail);
    }
}
