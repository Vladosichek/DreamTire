package com.example.demo.mapper;

import com.example.demo.dto.MaterialRequestDTO;
import com.example.demo.dto.MaterialResponseDTO;
import com.example.demo.entity.Material;
import com.example.demo.entity.Station;

public class MaterialMapper {

    public static Material toEntity(MaterialRequestDTO dto, Station station) {
        return new Material(dto.getName(), dto.getDescription(), dto.getQuantity(), station, dto.getCritical());
    }

    public static MaterialResponseDTO toResponseDTO(Material material) {
        return new MaterialResponseDTO(
                material.getId(),
                material.getName(),
                material.getDescription(),
                material.getQuantity(),
                material.getStation().getId(),
                material.getCritical()
        );
    }

    public static void updateFromDTO(Material material, MaterialRequestDTO dto, Station station) {
        material.setName(dto.getName());
        material.setDescription(dto.getDescription());
        material.setQuantity(dto.getQuantity());
        material.setStation(station);
        material.setCritical(dto.getCritical());
    }
}