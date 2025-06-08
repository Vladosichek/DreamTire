package com.example.demo.repository;

import com.example.demo.entity.Station;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findByRole(Role role);

    Optional<User> findByEmail(String email);

    List<User> findByRoleAndStation(Role role, Station station);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.station IS NULL")
    List<User> findByRoleAndStationIsNull(@Param("role") Role role);
}
