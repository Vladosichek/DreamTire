package com.example.demo.controller;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        return userService.createUser(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public List<UserResponseDTO> getByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping
    public List<UserResponseDTO> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        if (name != null && !name.isBlank()) {
            return userService.getUsersByName(name);
        } else if (email != null && !email.isBlank()) {
            return userService.getUserByEmail(email)
                    .map(List::of)
                    .orElse(List.of());
        } else {
            return userService.getAllUsers();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        return userService.updateUser(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Не вдалося видалити користувача. Можливо, є залежні записи.");
        }
    }

    @PostMapping("/{id}/send-message")
    public ResponseEntity<?> sendMessageToUser(@PathVariable Long id, @RequestBody String message) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        if (user.getEmail() == null || !user.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return ResponseEntity.badRequest().body("Email адреса недійсна або відсутня.");
        }
        userService.sendEmailToUser(user, message);
        return ResponseEntity.ok().body("Повідомлення надіслано.");
    }

}

