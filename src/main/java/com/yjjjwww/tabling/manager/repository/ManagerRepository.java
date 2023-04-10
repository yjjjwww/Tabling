package com.yjjjwww.tabling.manager.repository;

import com.yjjjwww.tabling.manager.entity.Manager;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUserId(String userId);
}
