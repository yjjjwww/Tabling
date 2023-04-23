package com.yjjjwww.tabling.review.service;

import com.yjjjwww.tabling.review.model.ReviewDto;
import java.util.List;

public interface ReviewService {

    /**
     * 모든 리뷰 최신순으로 조회
     */
    List<ReviewDto> getLatestReviewList();

    /**
     * 매장별 리뷰 최신순으로 조회
     */
    List<ReviewDto> getReviewListByRestaurantId(Long restaurantId);
}
