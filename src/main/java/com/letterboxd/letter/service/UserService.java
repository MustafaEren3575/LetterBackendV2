package com.letterboxd.letter.service;

import com.letterboxd.letter.dto.AuthResponse;
import com.letterboxd.letter.dto.UserLoginRequest;
import com.letterboxd.letter.dto.UserRegisterRequest;
import com.letterboxd.letter.dto.UserResponse;
import com.letterboxd.letter.entities.User;

public interface UserService {
    User registerUser(UserRegisterRequest request);
    AuthResponse login(UserLoginRequest request);
    UserResponse getCurrentUserProfile();
}
