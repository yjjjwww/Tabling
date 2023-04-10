package com.yjjjwww.tabling.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    Long id;
    String userId;
    String password;
    String phone;
}
