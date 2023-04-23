package com.yjjjwww.tabling.customer.model;

import lombok.Data;

@Data
public class RegisterReviewForm {

    Long reservationId;
    String contents;
    Integer rating;
}
