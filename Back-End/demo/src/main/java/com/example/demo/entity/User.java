package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "telephone", nullable = false, length = 45)
    private String telephone;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 45)
    private Role role;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = true)
    private Station station;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessionsMaster;

    public User(String telephone, String email, String name, Role role, String password, Station station){
        this.telephone = telephone;
        this.email = email;
        this.name = name;
        this.role = role;
        this.password = password;
        this.station = station;
    }

    public User() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Station getStation(){
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Car> getCars(){
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Session> getSessions(){
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
    public List<Session> getSessionsMaster(){
        return sessionsMaster;
    }

    public void setSessionsMaster(List<Session> sessionsMaster) {
        this.sessionsMaster = sessionsMaster;
    }
}