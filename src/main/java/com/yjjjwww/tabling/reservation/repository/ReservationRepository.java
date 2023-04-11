package com.yjjjwww.tabling.reservation.repository;

import com.yjjjwww.tabling.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
