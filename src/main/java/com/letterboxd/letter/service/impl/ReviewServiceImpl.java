package com.letterboxd.letter.service.impl;

import com.letterboxd.letter.dto.ReviewRequest;
import com.letterboxd.letter.dto.ReviewResponse;
import com.letterboxd.letter.entities.Movie;
import com.letterboxd.letter.entities.Review;
import com.letterboxd.letter.entities.User;
import com.letterboxd.letter.exception.ResourceNotFoundException;
import com.letterboxd.letter.exception.UnauthorizedAccessException;
import com.letterboxd.letter.mapper.ReviewMapper;
import com.letterboxd.letter.repository.MovieRepository;
import com.letterboxd.letter.repository.ReviewRepository;
import com.letterboxd.letter.repository.UserRepository;
import com.letterboxd.letter.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;



    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow();

        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .movie(movie)
                .user(user)
                .build();

        Review savedReview =  reviewRepository.save(review);
        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    public ReviewResponse updateReview(Long id, ReviewRequest request) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No comments found!"));

        verifyReviewOwnership(review);

        review.setContent(request.getContent());
        review.setRating(request.getRating());

        Review updatedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No commonts found!"));

        verifyReviewOwnership(review);

        reviewRepository.delete(review);
    }

    @Override
    public Page<ReviewResponse> getCurrentUserReviews(int page, int size) {


        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();


        Pageable pageable = PageRequest.of(page, size);


        Page<Review> userReviews = reviewRepository.findByUserEmail(currentUserEmail, pageable);


        return userReviews.map(reviewMapper::toReviewResponse);
    }




    private void verifyReviewOwnership(Review review) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));


        if (!review.getUser().getEmail().equals(currentUserEmail) && !isAdmin) {
            throw new UnauthorizedAccessException("Comment is't yours, you can't delete or edit it.");
        }
    }
}
