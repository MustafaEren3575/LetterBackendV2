package com.letterboxd.letter.controller;

import com.letterboxd.letter.dto.ReviewRequest;
import com.letterboxd.letter.dto.ReviewResponse;
import com.letterboxd.letter.entities.Review;
import com.letterboxd.letter.mapper.ReviewMapper;
import com.letterboxd.letter.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody ReviewRequest request) {
        ReviewResponse reviewResponse = reviewService.addReview(request);


        return new ResponseEntity<>(reviewResponse, HttpStatus.CREATED);
    }

    // ... diğer metotlar

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequest request) {
        ReviewResponse updatedReview = reviewService.updateReview(id, request);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }


}
