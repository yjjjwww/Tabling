package com.yjjjwww.tabling.review.controller;

import com.yjjjwww.tabling.review.model.ReviewDto;
import com.yjjjwww.tabling.review.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "모든 리뷰 최신순으로 조회")
    @GetMapping
    public List<ReviewDto> getLatestReviewList() {
        return reviewService.getLatestReviewList();
    }

    @ApiOperation(value = "매장별 리뷰 최신순으로 조회")
    @GetMapping("/{restaurantId}")
    public List<ReviewDto> getReviewListByRestaurantId(
        @PathVariable Long restaurantId
    ) {
        return reviewService.getReviewListByRestaurantId(restaurantId);
    }
}
