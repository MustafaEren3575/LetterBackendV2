package com.letterboxd.letter.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbSearchResponse {
    private List<TmdbMovieResponse> results;
}
