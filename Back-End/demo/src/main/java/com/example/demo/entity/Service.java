package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "cost", nullable = false)
    private Float cost;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionServices> sessionServices;

    public Service(String name, String description, Station station, Long duration, Float cost){
        this.name = name;
        this.description = description;
        this.station = station;
        this.duration = duration;
        this.cost = cost;
    }

    public Service() {}

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

    public Station getStation(){
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    public List<SessionServices> getSessionServices(){
        return sessionServices;
    }

    public void setSessionServices(List<SessionServices> sessionServices) {
        this.sessionServices = sessionServices;
    }
}