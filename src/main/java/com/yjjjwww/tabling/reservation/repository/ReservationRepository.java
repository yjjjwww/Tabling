package com.yjjjwww.tabling.reservation.repository;

import com.yjjjwww.tabling.reservation.entity.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByManagerIdAndAcceptedFalse(Long managerId);

    Optional<Reservation> findByReservationCode(String reservationCode);
}
