package com.example.demo.service;

import com.example.demo.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.StationRepository;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.Station;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final JavaMailSender mailSender;

    public AuthService(UserRepository userRepository,
                       StationRepository stationRepository,
                       JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
        this.mailSender = mailSender;
    }

    public Optional<UserResponseDTO> registerUser(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return Optional.empty();
        }
        Station station = null;
        if (dto.getStationId() != null) {
            station = stationRepository.findById(dto.getStationId()).orElse(null);
        }
        User newUser = UserMapper.toEntity(dto, station);
        newUser.setRole(Role.CLIENT);
        User saved = userRepository.save(newUser);
        sendConfirmationEmail(saved);
        return Optional.of(UserMapper.toResponseDTO(saved));
    }

    private void sendConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Підтвердження реєстрації");
        message.setText("Дякуємо за реєстрацію, " + user.getName() + "!");
        mailSender.send(message);
    }

    public ResponseEntity<?> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);
    }
}

