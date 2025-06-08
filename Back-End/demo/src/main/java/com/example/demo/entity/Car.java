package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User  user;

    @Column(name = "number", nullable = false, unique = true, length = 45)
    private String number;

    @Column(name = "make", nullable = false, length = 45)
    private String make;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;

    public Car(User user, String number, String make, String model){
        this.user = user;
        this.number = number;
        this.make = make;
        this.model = model;
    }

    public Car() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Session> getSessions(){
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}