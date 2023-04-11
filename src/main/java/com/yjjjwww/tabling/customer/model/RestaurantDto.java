package com.yjjjwww.tabling.customer.model;

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
public class RestaurantDto {

    Long id;
    String name;
    String address;
    String phone;
    String description;

    ManagerDto manager;
}
