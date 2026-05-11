package com.letterboxd.letter.service.impl;

import com.letterboxd.letter.dto.*;
import com.letterboxd.letter.entities.Movie;
import com.letterboxd.letter.exception.ResourceNotFoundException;
import com.letterboxd.letter.mapper.MovieMapper;
import com.letterboxd.letter.repository.MovieRepository;
import com.letterboxd.letter.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${tmdb.api.url}")
    private String tmdbApiUrl;

    @Value("${tmdb.api.search-url}")
    private String tmdbSearchUrl;


    @Override
    public MovieResponse addMovie(MovieRequest request) {
        Movie movie = movieMapper.toEntity(request);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toMovieResponse(savedMovie);

    }

    @Override
    public MovieResponse addMovieFromTmdb(Long tmdbId) {
        String url = tmdbApiUrl + tmdbId + "?api_key=" + tmdbApiKey + "&language=tr-TR";

        TmdbMovieResponse tmdbResponse = restTemplate.getForObject(url,TmdbMovieResponse.class);
        if (tmdbResponse == null || tmdbResponse.getTitle() == null) {
            throw new ResourceNotFoundException("TMDB sunucularında bu ID ile bir film bulunamadı: " + tmdbId);
        }

        Movie movie = Movie.builder()
                .id(tmdbId)
                .title(tmdbResponse.getTitle())
                .overview(tmdbResponse.getOverview())
                .releaseDate(tmdbResponse.getReleaseDate())
                .build();

        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toMovieResponse(savedMovie);

    }

    @Override
    public List<TmdbMovieResponse> searchMoviesFromTmdb(String query) {
        String url = tmdbSearchUrl + "?api_key=" + tmdbApiKey + "&language=tr-TR&query=" + query;

        TmdbSearchResponse searchResponse = restTemplate.getForObject(url, TmdbSearchResponse.class);

        if (searchResponse == null || searchResponse.getResults() == null) {
            return List.of();
        }

        return searchResponse.getResults();
    }

    @Override
    public Page<MovieResponse> getAllMovies(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Movie> moviePage = movieRepository.findAll(pageable);

        return moviePage.map(movieMapper::toMovieResponse);


    }

    @Override
    public MovieDetailsResponse getMovieById(Long id) {
        Movie movieFromDb = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no movie with " + id + " id."));

        MovieDetailsResponse movieDetailsResponse = movieMapper.toMovieDetailsResponse(movieFromDb);

        return movieDetailsResponse;
    }

    @Override
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Silinmek istenen film bulunamadı! ID: " + id));

        movieRepository.delete(movie);
    }

}
