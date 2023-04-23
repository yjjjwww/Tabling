package com.yjjjwww.tabling.review.entity;

import com.yjjjwww.tabling.customer.entity.Customer;
import com.yjjjwww.tabling.entity.BaseEntity;
import com.yjjjwww.tabling.reservation.entity.Reservation;
import com.yjjjwww.tabling.restaurant.entity.Restaurant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 리뷰 내용
    String contents;
    // 리뷰 평점(1~10)
    Integer rating;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;
}
