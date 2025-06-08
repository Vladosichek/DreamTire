package com.example.demo.mapper;

import com.example.demo.dto.StationRequestDTO;
import com.example.demo.dto.StationResponseDTO;
import com.example.demo.entity.Station;

public class StationMapper {

    public static Station toEntity(StationRequestDTO dto) {
        return new Station(dto.getName(), dto.getAdress(), dto.getMonStart(), dto.getMonFinish(), dto.getTueStart(), dto.getTueFinish(), dto.getWenStart(), dto.getWenFinish(), dto.getThuStart(), dto.getThuFinish(), dto.getFriStart(), dto.getFriFinish(), dto.getSatStart(), dto.getSatFinish(), dto.getSunStart(), dto.getSunFinish());
    }

    public static StationResponseDTO toResponseDTO(Station station) {
        return new StationResponseDTO(
                station.getId(),
                station.getName(),
                station.getAdress(),
                station.getMonStart(),
                station.getMonFinish(),
                station.getTueStart(),
                station.getTueFinish(),
                station.getWenStart(),
                station.getWenFinish(),
                station.getThuStart(),
                station.getThuFinish(),
                station.getFriStart(),
                station.getFriFinish(),
                station.getSatStart(),
                station.getSatFinish(),
                station.getSunStart(),
                station.getSunFinish()
        );
    }

    public static void updateFromDTO(Station station, StationRequestDTO dto) {
        station.setName(dto.getName());
        station.setAdress(dto.getAdress());
        station.setMonStart(dto.getMonStart());
        station.setMonFinish(dto.getMonFinish());
        station.setTueStart(dto.getTueStart());
        station.setTueFinish(dto.getTueFinish());
        station.setWenStart(dto.getWenStart());
        station.setWenFinish(dto.getWenFinish());
        station.setThuStart(dto.getThuStart());
        station.setThuFinish(dto.getThuFinish());
        station.setFriStart(dto.getFriStart());
        station.setFriFinish(dto.getFriFinish());
        station.setSatStart(dto.getSatStart());
        station.setSatFinish(dto.getSatFinish());
        station.setSunStart(dto.getSunStart());
        station.setSunFinish(dto.getSunFinish());
    }
}