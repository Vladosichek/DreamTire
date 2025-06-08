package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StationStatResponseDTO {
    private List<String> stationNames;
    private List<LocalDate> dates;
    private Map<LocalDate, Map<String, Number>> data;
    private Map<String, Number> totalPerStation;

    public StationStatResponseDTO() {}

    public List<String> getStationNames(){
        return stationNames;
    }

    public void setStationNames(List<String> stationNames) {
        this.stationNames = stationNames;
    }

    public List<LocalDate> getDates(){
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public Map<LocalDate, Map<String, Number>> getData(){
        return data;
    }

    public void setData(Map<LocalDate, Map<String, Number>> data) {
        this.data = data;
    }

    public Map<String, Number> getTotalPerStation(){
        return totalPerStation;
    }

    public void setTotalPerStation(Map<String, Number> totalPerStation) {
        this.totalPerStation = totalPerStation;
    }
}

