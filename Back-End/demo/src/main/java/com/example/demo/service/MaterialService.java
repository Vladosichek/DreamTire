package com.example.demo.service;

import com.example.demo.dto.MaterialRequestDTO;
import com.example.demo.dto.MaterialResponseDTO;
import com.example.demo.entity.Material;
import com.example.demo.entity.Station;
import com.example.demo.mapper.MaterialMapper;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final StationRepository stationRepository;

    public MaterialService(MaterialRepository materialRepository, StationRepository stationRepository) {
        this.materialRepository = materialRepository;
        this.stationRepository = stationRepository;
    }

    public Optional<MaterialResponseDTO> createMaterial(MaterialRequestDTO dto) {
        Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
        if (stationOpt.isEmpty()) {
            return Optional.empty();
        }
        Material material = MaterialMapper.toEntity(dto, stationOpt.get());
        Material saved = materialRepository.save(material);
        return Optional.of(MaterialMapper.toResponseDTO(saved));
    }

    public Optional<MaterialResponseDTO> getMaterialById(Long id) {
        return materialRepository.findById(id)
                .map(MaterialMapper::toResponseDTO);
    }

    public List<MaterialResponseDTO> getAllMaterials() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MaterialResponseDTO> getMaterialsByStationId(Long stationId) {
        return materialRepository.findAllByStation(
                        stationRepository.findById(stationId).orElse(null)
                ).stream()
                .map(MaterialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MaterialResponseDTO> getCriticalMaterialsByStationId(Long stationId) {
        return materialRepository.findCriticalMaterialsByStationId(stationId)
                .stream()
                .map(MaterialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<MaterialResponseDTO> updateMaterial(Long id, MaterialRequestDTO dto) {
        Optional<Material> materialOpt = materialRepository.findById(id);
        Optional<Station> stationOpt = stationRepository.findById(dto.getStationId());
        if (materialOpt.isEmpty() || stationOpt.isEmpty()) {
            return Optional.empty();
        }
        Material material = materialOpt.get();
        MaterialMapper.updateFromDTO(material, dto, stationOpt.get());
        Material updated = materialRepository.save(material);
        return Optional.of(MaterialMapper.toResponseDTO(updated));
    }

    public boolean deleteMaterial(Long id) {
        if (materialRepository.existsById(id)) {
            materialRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
