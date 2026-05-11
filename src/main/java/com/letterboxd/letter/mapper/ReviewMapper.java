package com.letterboxd.letter.mapper;

import com.letterboxd.letter.dto.ReviewResponse;
import com.letterboxd.letter.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(source = "user.username", target = "username")
    ReviewResponse toReviewResponse(Review review);
}
