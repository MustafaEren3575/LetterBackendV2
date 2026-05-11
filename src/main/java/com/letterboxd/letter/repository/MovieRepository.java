package com.letterboxd.letter.repository;

import com.letterboxd.letter.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
