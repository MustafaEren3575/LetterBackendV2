package com.letterboxd.letter.controller;

import com.letterboxd.letter.dto.*;
import com.letterboxd.letter.entities.User;
import com.letterboxd.letter.mapper.UserMapper;
import com.letterboxd.letter.service.ReviewService;
import com.letterboxd.letter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {

        User savedUser = userService.registerUser(request);

        UserResponse response = userMapper.toUserResponse(savedUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/secret")
    public ResponseEntity<String> secretRoom() {
        return ResponseEntity.ok("Welcome to the Secret Room. Your token is working!");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @GetMapping("/me/reviews")
    public ResponseEntity<Page<ReviewResponse>> getMyReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reviewService.getCurrentUserReviews(page, size));
    }

}
