package com.example.demo.repository;

import com.example.demo.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s FROM Service s WHERE s.station.id = :stationId")
    List<Service> findAllByStationId(@Param("stationId") Long stationId);
}
