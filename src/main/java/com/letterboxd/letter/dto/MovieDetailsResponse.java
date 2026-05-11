package com.letterboxd.letter.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MovieDetailsResponse {

    private Long id;
    private String title;
    private String overview;
    private LocalDate releaseDate;
    private List<ReviewResponse> reviews = new ArrayList<>();
    private Integer reviewCount;
}
