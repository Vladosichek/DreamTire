package com.example.demo.dto;

public class StationResponseDTO {
    private Long id;
    private String name;
    private String adress;
    private Long monStart;
    private Long monFinish;
    private Long tueStart;
    private Long tueFinish;
    private Long wenStart;
    private Long wenFinish;
    private Long thuStart;
    private Long thuFinish;
    private Long friStart;
    private Long friFinish;
    private Long satStart;
    private Long satFinish;
    private Long sunStart;
    private Long sunFinish;

    public StationResponseDTO(Long id, String name, String adress, Long monStart, Long monFinish, Long tueStart, Long tueFinish, Long wenStart, Long wenFinish, Long thuStart, Long thuFinish, Long friStart, Long friFinish, Long satStart, Long satFinish, Long sunStart, Long sunFinish) {
        this.id = id;
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

    public StationResponseDTO() {
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
}
