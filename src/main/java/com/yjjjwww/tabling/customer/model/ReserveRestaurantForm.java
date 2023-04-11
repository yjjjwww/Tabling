package com.yjjjwww.tabling.customer.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReserveRestaurantForm {

    Long restaurantId;
    LocalDateTime reservationTime;
}
