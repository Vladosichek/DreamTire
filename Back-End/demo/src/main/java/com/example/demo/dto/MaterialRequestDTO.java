package com.example.demo.dto;

public class MaterialRequestDTO {
    private String  name;
    private String description;
    private Long quantity;
    private Long stationId;
    private Long critical;

    public MaterialRequestDTO(String name, String description, Long quantity, Long stationId, Long critical){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.stationId = stationId;
        this.critical = critical;
    }

    public MaterialRequestDTO() {}

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

    public Long getQuantity(){
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getStationId(){
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getCritical(){
        return critical;
    }

    public void setCritical(Long critical) {
        this.critical = critical;
    }
}
