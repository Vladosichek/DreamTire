package com.example.demo.dto;

public class CarRequestDTO {

    private Long  userId;
    private String number;
    private String make;
    private String model;

    public CarRequestDTO(Long userId, String number, String make, String model){
        this.userId = userId;
        this.number = number;
        this.make = make;
        this.model = model;
    }

    public CarRequestDTO() {}

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMake(){
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel(){
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}