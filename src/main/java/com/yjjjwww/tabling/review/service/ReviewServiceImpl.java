package com.yjjjwww.tabling.review.service;

import com.yjjjwww.tabling.review.entity.Review;
import com.yjjjwww.tabling.review.model.ReviewDto;
import com.yjjjwww.tabling.review.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getLatestReviewList() {
        List<ReviewDto> result = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByOrderByCreatedAtDesc();
        for (Review review : reviews) {
            ReviewDto reviewDto = ReviewDto.builder()
                .id(review.getId())
                .contents(review.getContents())
                .rating(review.getRating())
                .restaurantName(review.getRestaurant().getName())
                .customerUserId(review.getCustomer().getUserId())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
            result.add(reviewDto);
        }
        return result;
    }

    @Override
    public List<ReviewDto> getReviewListByRestaurantId(Long restaurantId) {
        List<ReviewDto> result = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByRestaurantIdOrderByCreatedAtDesc(
            restaurantId);
        for (Review review : reviews) {
            ReviewDto reviewDto = ReviewDto.builder()
                .id(review.getId())
                .contents(review.getContents())
                .rating(review.getRating())
                .restaurantName(review.getRestaurant().getName())
                .customerUserId(review.getCustomer().getUserId())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
            result.add(reviewDto);
        }
        return result;
    }
}
