package com.letterboxd.letter.service.impl;

import com.letterboxd.letter.dto.AuthResponse;
import com.letterboxd.letter.dto.UserLoginRequest;
import com.letterboxd.letter.dto.UserRegisterRequest;
import com.letterboxd.letter.dto.UserResponse;
import com.letterboxd.letter.entities.User;
import com.letterboxd.letter.entities.enums.Role;
import com.letterboxd.letter.exception.ResourceNotFoundException;
import com.letterboxd.letter.exception.UserAlreadyExistsException;
import com.letterboxd.letter.mapper.UserMapper;
import com.letterboxd.letter.repository.UserRepository;
import com.letterboxd.letter.security.JwtService;
import com.letterboxd.letter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public User registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("This email already in use");
        }

        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("This username already in use");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public AuthResponse login(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).build();
    }

    public UserResponse getCurrentUserProfile() {

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();


        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));


        return userMapper.toUserResponse(user);
    }
}
