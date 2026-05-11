package com.letterboxd.letter.mapper;

import com.letterboxd.letter.dto.MovieDetailsResponse;
import com.letterboxd.letter.dto.MovieRequest;
import com.letterboxd.letter.dto.MovieResponse;
import com.letterboxd.letter.entities.Movie;
import com.letterboxd.letter.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class})
public interface MovieMapper {


    Movie toEntity(MovieRequest request);
    MovieResponse toMovieResponse(Movie movie);

    @Mapping(source = "reviews", target = "reviewCount", qualifiedByName = "calculateReviewCount")
    MovieDetailsResponse toMovieDetailsResponse(Movie movie);

    @Named("calculateReviewCount")
    default Integer calculateReviewCount(List<Review> reviews) {
        if(reviews == null) {
            return 0;
        }

        return Math.toIntExact(reviews.stream().count());

    }

}
