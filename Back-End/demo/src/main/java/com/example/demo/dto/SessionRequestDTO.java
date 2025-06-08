package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.Status;

public class SessionRequestDTO {
    private LocalDate date;
    private Long customerId;
    private Long masterId;
    private Long carId;
    private Long time;
    private Long duration;
    private Float cost;
    private Long stationId;
    private Float discount;
    private Status status;
    private String description;
    private List<SessionServiceItemDTO> services;

    public SessionRequestDTO(LocalDate date, Long customerId, Long masterId, Long carId, Long time, Long duration, Float cost, Long stationId, Float discount, Status status, String description, List<SessionServiceItemDTO> services){
        this.date = date;
        this.customerId = customerId;
        this.masterId = masterId;
        this.carId = carId;
        this.time = time;
        this.duration = duration;
        this.cost = cost;
        this.stationId = stationId;
        this.discount = discount;
        this.status = status;
        this.description = description;
        this.services = services;
    }

    public SessionRequestDTO() {}

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCustomerId(){
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMasterId(){
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Long getCarId(){
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getTime(){
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getDuration(){
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Float getCost(){
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Long getStationId(){
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Float getDiscount(){
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SessionServiceItemDTO> getServices(){
        return services;
    }

    public void setServices(List<SessionServiceItemDTO> services) {
        this.services = services;
    }
}
