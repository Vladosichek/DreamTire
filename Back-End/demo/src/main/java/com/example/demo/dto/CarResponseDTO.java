package com.example.demo.dto;

public class CarResponseDTO {

    private Long id;
    private Long  userId;
    private String number;
    private String make;
    private String model;
    private String userName;

    public CarResponseDTO(Long id, Long userId, String number, String make, String model, String userName){
        this.id = id;
        this.userId = userId;
        this.number = number;
        this.make = make;
        this.model = model;
        this.userName = userName;
    }

    public CarResponseDTO() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}