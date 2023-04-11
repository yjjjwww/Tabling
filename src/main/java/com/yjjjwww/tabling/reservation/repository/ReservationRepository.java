package com.yjjjwww.tabling.reservation.repository;

import com.yjjjwww.tabling.reservation.entity.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByManagerIdAndAcceptedFalse(Long managerId);
}
