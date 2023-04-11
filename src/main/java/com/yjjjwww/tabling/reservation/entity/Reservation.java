package com.yjjjwww.tabling.reservation.entity;

import com.yjjjwww.tabling.customer.entity.Customer;
import com.yjjjwww.tabling.entity.BaseEntity;
import com.yjjjwww.tabling.manager.entity.Manager;
import com.yjjjwww.tabling.restaurant.entity.Restaurant;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime reservationTime;
    String reservationCode;
    boolean visited;
    boolean accepted;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @ToString.Exclude
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;
}
