package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String  name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(name = "critical", nullable = false)
    private Long critical;

    public Material(String name, String description, Long quantity, Station station, Long critical){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.station = station;
        this.critical = critical;
    }

    public Material() {}

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

    public Station getStation(){
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Long getCritical(){
        return critical;
    }

    public void setCritical(Long critical) {
        this.critical = critical;
    }
}