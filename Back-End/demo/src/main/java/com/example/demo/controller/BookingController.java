package com.example.demo.controller;

import com.example.demo.dto.BookingRequestDTO;
import com.example.demo.dto.SessionResponseDTO;
import com.example.demo.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/available-times")
    public ResponseEntity<List<Long>> getAvailableTimes(@RequestBody BookingRequestDTO dto) {
        List<Long> times = bookingService.findAvailableTimes(dto);
        return ResponseEntity.ok(times);
    }

    @PostMapping("/book")
    public ResponseEntity<SessionResponseDTO> bookSession(@RequestBody BookingRequestDTO dto) {
        Optional<SessionResponseDTO> session = bookingService.bookSession(dto);
        return session.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
