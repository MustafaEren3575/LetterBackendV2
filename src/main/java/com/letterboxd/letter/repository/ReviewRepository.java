package com.letterboxd.letter.repository;

import com.letterboxd.letter.entities.Movie;
import com.letterboxd.letter.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie(Movie movie);
    Page<Review> findByUserEmail(String email, Pageable pageable);

}
