package com.yjjjwww.tabling.review.repository;

import com.yjjjwww.tabling.review.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReservationId(Long reservationId);

    List<Review> findAllByOrderByCreatedAtDesc();

    List<Review> findAllByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}
