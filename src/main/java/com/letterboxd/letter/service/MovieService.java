package com.letterboxd.letter.service;

import com.letterboxd.letter.dto.MovieDetailsResponse;
import com.letterboxd.letter.dto.MovieRequest;
import com.letterboxd.letter.dto.MovieResponse;
import com.letterboxd.letter.dto.TmdbMovieResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    MovieResponse addMovie(MovieRequest request);
    MovieResponse addMovieFromTmdb(Long tmdbId);
    Page<MovieResponse> getAllMovies(int page, int size);
    MovieDetailsResponse getMovieById(Long id);
    List<TmdbMovieResponse> searchMoviesFromTmdb(String query);
    void deleteMovie(Long id);


}
