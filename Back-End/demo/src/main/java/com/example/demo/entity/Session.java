package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", nullable = true)
    private User master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "time", nullable = false)
    private Long time;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "cost", nullable = false)
    private Float cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "discount", nullable = true)
    private Float discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 45)
    private Status status;

    @Column(name = "description", nullable = true, length = 200)
    private String description;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionServices> sessionServices;

    public Session(LocalDate date, User customer, User master, Car car, Long time, Long duration, Float cost, Station station, Float discount, Status status, String description){
        this.date = date;
        this.customer = customer;
        this.master = master;
        this.car = car;
        this.time = time;
        this.duration = duration;
        this.cost = cost;
        this.station = station;
        this.discount = discount;
        this.status = status;
        this.description = description;
    }

    public Session() {}

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getCustomer(){
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public User getMaster(){
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public Car getCar(){
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
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

    public Station getStation(){
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    public List<SessionServices> getSessionServices(){
        return sessionServices;
    }

    public void setSessionServices(List<SessionServices> sessionServices) {
        this.sessionServices = sessionServices;
    }
}