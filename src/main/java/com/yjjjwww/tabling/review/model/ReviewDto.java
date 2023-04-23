package com.yjjjwww.tabling.review.model;

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
public class ReviewDto {

    Long id;
    String contents;
    Integer rating;
    String restaurantName;
    String customerUserId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
