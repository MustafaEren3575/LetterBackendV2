package com.letterboxd.letter.controller;

import com.letterboxd.letter.dto.MovieDetailsResponse;
import com.letterboxd.letter.dto.MovieRequest;
import com.letterboxd.letter.dto.MovieResponse;
import com.letterboxd.letter.dto.TmdbMovieResponse;
import com.letterboxd.letter.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping()
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody MovieRequest request) {

        MovieResponse movieResponse = movieService.addMovie(request);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);

    }

    @PostMapping("/tmdb/{tmdbId}")
    public ResponseEntity<MovieResponse> addMovieFromTmdb(@PathVariable Long tmdbId) {

        MovieResponse movieResponse = movieService.addMovieFromTmdb(tmdbId);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<MovieResponse>> listAllMovies(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        Page<MovieResponse> movies = movieService.getAllMovies(page, size);

        return new ResponseEntity<>(movies, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailsResponse> getMovieById(@PathVariable Long id) {

        MovieDetailsResponse movie = movieService.getMovieById(id);

        return ResponseEntity.ok(movie);

    }

    @GetMapping("/search")
    public ResponseEntity<List<TmdbMovieResponse>> searchMovies(@RequestParam String query) {

        List<TmdbMovieResponse> results = movieService.searchMoviesFromTmdb(query);

        return ResponseEntity.ok(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }

}
