package com.letterboxd.letter.mapper;

import com.letterboxd.letter.dto.UserResponse;
import com.letterboxd.letter.entities.Review;
import com.letterboxd.letter.entities.User;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "reviews", target = "reviewCount", qualifiedByName = "calculateReviewCount")
    UserResponse toUserResponse(User user);

    @Named("calculateReviewCount")
    default int calculateReviewCount(List<Review> reviews){

        if (reviews == null) {
            return 0;
        }

        return reviews.size();
    }



}
