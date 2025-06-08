package com.example.demo.dto;

import com.example.demo.entity.Status;

import java.time.LocalDate;
import java.util.List;

public class BookingRequestDTO {
    private Long stationId;
    private Long customerId;
    private Long masterId;
    private Long carId;
    private LocalDate date;
    private Long startTime;
    private Long duration;
    private Float cost;
    private Float discount;
    private String description;
    private Status status; // Припускаю, Status — enum
    private List<SessionServiceItemDTO> services;

    public BookingRequestDTO() {}

    public BookingRequestDTO(Long stationId, Long customerId, Long masterId, Long carId, LocalDate date,
                             Long startTime, Long duration, Float cost, Float discount,
                             String description, Status status, List<SessionServiceItemDTO> services) {
        this.stationId = stationId;
        this.customerId = customerId;
        this.masterId = masterId;
        this.carId = carId;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.cost = cost;
        this.discount = discount;
        this.description = description;
        this.status = status;
        this.services = services;
    }

    public Long getStationId() { return stationId; }
    public void setStationId(Long stationId) { this.stationId = stationId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getMasterId() { return masterId; }
    public void setMasterId(Long masterId) { this.masterId = masterId; }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getStartTime() { return startTime; }
    public void setStartTime(Long startTime) { this.startTime = startTime; }

    public Long getDuration() { return duration; }
    public void setDuration(Long duration) { this.duration = duration; }

    public Float getCost() { return cost; }
    public void setCost(Float cost) { this.cost = cost; }

    public Float getDiscount() { return discount; }
    public void setDiscount(Float discount) { this.discount = discount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<SessionServiceItemDTO> getServices() { return services; }
    public void setServices(List<SessionServiceItemDTO> services) { this.services = services; }
}

