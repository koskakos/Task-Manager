package com.task.manager.service;

import com.task.manager.model.User;
import com.task.manager.model.UserInfo;
import com.task.manager.model.request.SignInRequest;
import com.task.manager.model.request.SignUpRequest;
import com.task.manager.model.response.JwtAuthenticationResponse;
import com.task.manager.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService{
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .userInfo(UserInfo.builder().firstName(request.getFirstName()).lastName(request.getLastName()).build())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .build();
        userService.saveUser(user);
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user).getRefreshToken())
                .build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findUserByEmail(request.getEmail());
        refreshTokenRepository.deleteById(user.getId());
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user).getRefreshToken())
                .build();
    }

    public JwtAuthenticationResponse refresh(String refreshToken) {
        User user = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new NoSuchElementException("incorrect refresh token")).getUser();
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
        return JwtAuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user).getRefreshToken())
                .build();
    }
}
