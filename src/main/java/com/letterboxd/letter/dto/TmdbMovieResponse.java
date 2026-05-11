package com.letterboxd.letter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TmdbMovieResponse {

    private Long id;

    private String title;

    private String overview;

    // Our movie entity holds date as releaseDate
    @JsonProperty("release_date")
    private LocalDate releaseDate;

}
