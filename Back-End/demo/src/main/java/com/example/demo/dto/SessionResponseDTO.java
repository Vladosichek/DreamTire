package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public class SessionResponseDTO {
    private Long id;
    private LocalDate date;
    private Long time;
    private Long duration;
    private Float cost;
    private Float discount;
    private String description;
    private String status;
    private Long customerId;
    private Long masterId;
    private Long carId;
    private Long stationId;
    private List<SessionServiceItemDTO> services;

    public SessionResponseDTO() {}

    public SessionResponseDTO(Long id, LocalDate date, Long time, Long duration, Float cost, Float discount,
                              String description, String status, Long customerId, Long masterId,
                              Long carId, Long stationId, List<SessionServiceItemDTO> services) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.cost = cost;
        this.discount = discount;
        this.description = description;
        this.status = status;
        this.customerId = customerId;
        this.masterId = masterId;
        this.carId = carId;
        this.stationId = stationId;
        this.services = services;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getTime() { return time; }
    public void setTime(Long time) { this.time = time; }

    public Long getDuration() { return duration; }
    public void setDuration(Long duration) { this.duration = duration; }

    public Float getCost() { return cost; }
    public void setCost(Float cost) { this.cost = cost; }

    public Float getDiscount() { return discount; }
    public void setDiscount(Float discount) { this.discount = discount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getMasterId() { return masterId; }
    public void setMasterId(Long masterId) { this.masterId = masterId; }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public Long getStationId() { return stationId; }
    public void setStationId(Long stationId) { this.stationId = stationId; }

    public List<SessionServiceItemDTO> getServices() { return services; }
    public void setServices(List<SessionServiceItemDTO> services) { this.services = services; }
}
