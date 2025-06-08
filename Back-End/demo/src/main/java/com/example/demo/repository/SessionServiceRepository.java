package com.example.demo.repository;

import com.example.demo.entity.SessionServices;
import com.example.demo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SessionServiceRepository extends JpaRepository<SessionServices, Long> {
    @Query("SELECT ss FROM SessionServices ss WHERE ss.session = :session")
    List<SessionServices> findAllBySession(@Param("session") Session session);

    List<SessionServices> findAllBySessionId(Long sessionId);

    @Modifying
    @Query("DELETE FROM SessionServices ss WHERE ss.session.id = :sessionId")
    void deleteBySessionId(@Param("sessionId") Long sessionId);

}
