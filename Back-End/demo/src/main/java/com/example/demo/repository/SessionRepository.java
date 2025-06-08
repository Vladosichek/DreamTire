package com.example.demo.repository;

import com.example.demo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.date = :date AND s.station.id = :stationId")
    List<Session> findByDateAndStationId(@Param("date") LocalDate date, @Param("stationId") Long stationId);

    @Query("SELECT s FROM Session s WHERE s.master.id = :masterId")
    List<Session> findByMasterId(@Param("masterId") Long masterId);

    @Query("SELECT s FROM Session s WHERE s.customer.id = :customerId")
    List<Session> findByCustomerId(@Param("customerId") Long customerId);

    @Query("""
    SELECT new map(
        s.date as date,
        st.name as station,
        SUM(
            CASE
                WHEN :type = 'BUSY' THEN s.duration
                WHEN :type = 'INCOME' THEN 
                    CASE 
                        WHEN s.discount IS NOT NULL AND s.discount >= 0 AND s.discount < 1 THEN s.cost * (1 - s.discount)
                        ELSE s.cost
                    END
                ELSE 0
            END
        ) as value
    )
    FROM Session s
    JOIN s.station st
    WHERE s.date BETWEEN :start AND :end
    AND (
        (:type = 'BUSY' AND s.status IN ('DONE', 'CLOSED')) OR
        (:type = 'INCOME' AND s.status = 'CLOSED')
    )
    GROUP BY s.date, st.name
""")
    List<Map<String, Object>> findStatData(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("type") String type
    );

}
