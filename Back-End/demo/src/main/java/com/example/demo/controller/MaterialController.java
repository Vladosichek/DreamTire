package com.example.demo.controller;

import com.example.demo.dto.MaterialRequestDTO;
import com.example.demo.dto.MaterialResponseDTO;
import com.example.demo.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@CrossOrigin
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<MaterialResponseDTO> createMaterial(@RequestBody MaterialRequestDTO dto) {
        return materialService.createMaterial(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public List<MaterialResponseDTO> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> getMaterialById(@PathVariable Long id) {
        return materialService.getMaterialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/station/{stationId}")
    public List<MaterialResponseDTO> getMaterialsByStation(@PathVariable Long stationId) {
        return materialService.getMaterialsByStationId(stationId);
    }

    @GetMapping("/critical/{stationId}")
    public List<MaterialResponseDTO> getCriticalMaterials(@PathVariable Long stationId) {
        return materialService.getCriticalMaterialsByStationId(stationId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> updateMaterial(@PathVariable Long id, @RequestBody MaterialRequestDTO dto) {
        return materialService.updateMaterial(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        if (materialService.deleteMaterial(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

