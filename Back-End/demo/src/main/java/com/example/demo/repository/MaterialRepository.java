package com.example.demo.repository;

import com.example.demo.entity.Material;
import com.example.demo.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findAllByStation(Station station);

    @Query("SELECT m FROM Material m WHERE m.station.id = :stationId AND m.quantity <= m.critical")
    List<Material> findCriticalMaterialsByStationId(@Param("stationId") Long stationId);

}
