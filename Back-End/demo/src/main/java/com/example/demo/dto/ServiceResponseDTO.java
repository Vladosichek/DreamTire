package com.example.demo.dto;

public class ServiceResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Long stationId;
    private Long duration;
    private Float cost;
    private String stationName;

    public ServiceResponseDTO(Long id, String name, String description, Long stationId, Long duration, Float cost, String stationName){
        this.id = id;
        this.name = name;
        this.description = description;
        this.stationId = stationId;
        this.duration = duration;
        this.cost = cost;
        this.stationName = stationName;
    }

    public ServiceResponseDTO() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStationId(){
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
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

    public String getStationName(){
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
