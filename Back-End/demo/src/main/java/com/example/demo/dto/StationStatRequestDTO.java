package com.example.demo.dto;

import java.time.LocalDate;

public class StationStatRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private StatType statType;

    public StationStatRequestDTO() {}

    public LocalDate getStartDate(){
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public StatType getStatType(){
        return statType;
    }

    public void setStatType(StatType statType) {
        this.statType = statType;
    }
}
