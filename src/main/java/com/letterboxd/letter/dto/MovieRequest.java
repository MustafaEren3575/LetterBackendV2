package com.letterboxd.letter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieRequest {
    @NotNull()
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String overview;

    @NotNull
    private LocalDate releaseDate;
}
