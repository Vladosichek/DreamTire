package com.example.demo.dto;

public class MaterialResponseDTO {
    private Long id;
    private String  name;
    private String description;
    private Long quantity;
    private Long stationId;
    private Long critical;

    public MaterialResponseDTO(Long id, String name, String description, Long quantity, Long stationId, Long critical){
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.stationId = stationId;
        this.critical = critical;
    }

    public MaterialResponseDTO() {}

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
