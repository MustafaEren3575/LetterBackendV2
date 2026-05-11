package com.letterboxd.letter.service;

import com.letterboxd.letter.dto.ReviewRequest;
import com.letterboxd.letter.dto.ReviewResponse;
import com.letterboxd.letter.entities.Review;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest request);
    ReviewResponse updateReview(Long id, ReviewRequest request);
    void deleteReview(Long id);
    Page<ReviewResponse> getCurrentUserReviews(int page, int size);
}
