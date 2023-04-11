package com.yjjjwww.tabling.manager.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantReservationDto {

    Long id;
    LocalDateTime reservationTime;

    ManagerRestaurantDto restaurant;
}
