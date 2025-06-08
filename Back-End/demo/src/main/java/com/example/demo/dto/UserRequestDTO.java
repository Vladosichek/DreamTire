package com.example.demo.dto;

import com.example.demo.entity.Role;

public class UserRequestDTO {
    private String telephone;
    private String email;
    private String name;
    private Role role;
    private String  password;
    private Long  stationId;

    public UserRequestDTO(String telephone, String email, String name, Role role, String password, Long stationId){
        this.telephone = telephone;
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.stationId = stationId;
    }

    public UserRequestDTO() {}

    public String getTelephone(){
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStationId(){
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
}
