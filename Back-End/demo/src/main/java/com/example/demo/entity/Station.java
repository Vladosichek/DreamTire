package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "adress", nullable = false, length = 200)
    private String adress;

    @Column(name = "mon_start", nullable = false)
    private Long monStart;

    @Column(name = "mon_finish", nullable = false)
    private Long monFinish;

    @Column(name = "tue_start", nullable = false)
    private Long tueStart;

    @Column(name = "tue_finish", nullable = false)
    private Long tueFinish;

    @Column(name = "wen_start", nullable = false)
    private Long wenStart;

    @Column(name = "wen_finish", nullable = false)
    private Long wenFinish;

    @Column(name = "thu_start", nullable = false)
    private Long thuStart;

    @Column(name = "thu_finish", nullable = false)
    private Long thuFinish;

    @Column(name = "fri_start", nullable = false)
    private Long friStart;

    @Column(name = "fri_finish", nullable = false)
    private Long friFinish;

    @Column(name = "sat_start", nullable = false)
    private Long satStart;

    @Column(name = "sat_finish", nullable = false)
    private Long satFinish;

    @Column(name = "sun_start", nullable = false)
    private Long sunStart;

    @Column(name = "sun_finish", nullable = false)
    private Long sunFinish;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materials;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> services;

    public Station(String name, String adress, Long monStart, Long monFinish, Long tueStart, Long tueFinish, Long wenStart, Long wenFinish, Long thuStart, Long thuFinish, Long friStart, Long friFinish, Long satStart, Long satFinish, Long sunStart, Long sunFinish) {
        this.name = name;
        this.adress = adress;
        this.monStart = monStart;
        this.monFinish = monFinish;
        this.tueStart = tueStart;
        this.tueFinish = tueFinish;
        this.wenStart = wenStart;
        this.wenFinish = wenFinish;
        this.thuStart = thuStart;
        this.thuFinish = thuFinish;
        this.friStart = friStart;
        this.friFinish = friFinish;
        this.satStart = satStart;
        this.satFinish = satFinish;
        this.sunStart = sunStart;
        this.sunFinish = sunFinish;
    }

    public Station() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Long getMonStart() {
        return monStart;
    }

    public void setMonStart(Long monStart) {
        this.monStart = monStart;
    }

    public Long getMonFinish() {
        return monFinish;
    }

    public void setMonFinish(Long monFinish) {
        this.monFinish = monFinish;
    }

    public Long getTueStart() {
        return tueStart;
    }

    public void setTueStart(Long tueStart) {
        this.tueStart = tueStart;
    }

    public Long getTueFinish() {
        return tueFinish;
    }

    public void setTueFinish(Long tueFinish) {
        this.tueFinish = tueFinish;
    }

    public Long getWenStart() {
        return wenStart;
    }

    public void setWenStart(Long wenStart) {
        this.wenStart = wenStart;
    }

    public Long getWenFinish() {
        return wenFinish;
    }

    public void setWenFinish(Long wenFinish) {
        this.wenFinish = wenFinish;
    }

    public Long getThuStart() {
        return thuStart;
    }

    public void setThuStart(Long thuStart) {
        this.thuStart = thuStart;
    }

    public Long getThuFinish() {
        return thuFinish;
    }

    public void setThuFinish(Long thuFinish) {
        this.thuFinish = thuFinish;
    }

    public Long getFriStart() {
        return friStart;
    }

    public void setFriStart(Long friStart) {
        this.friStart = friStart;
    }

    public Long getFriFinish() {
        return friFinish;
    }

    public void setFriFinish(Long friFinish) {
        this.friFinish = friFinish;
    }

    public Long getSatStart() {
        return satStart;
    }

    public void setSatStart(Long satStart) {
        this.satStart = satStart;
    }

    public Long getSatFinish() {
        return satFinish;
    }

    public void setSatFinish(Long satFinish) {
        this.satFinish = satFinish;
    }

    public Long getSunStart() {
        return sunStart;
    }

    public void setSunStart(Long sunStart) {
        this.sunStart = sunStart;
    }

    public Long getSunFinish() {
        return sunFinish;
    }

    public void setSunFinish(Long sunFinish) {
        this.sunFinish = sunFinish;
    }

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Session> getSessions(){
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public List<Material> getMaterials(){
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Service> getServices(){
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}