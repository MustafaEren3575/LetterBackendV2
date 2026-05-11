package com.letterboxd.letter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponse {

    private Long id;
    private String title;
    private String overview;
    private LocalDate releaseDate;
}
