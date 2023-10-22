package com.task.manager.service;

import com.task.manager.model.User;
import com.task.manager.model.UserInfo;
import com.task.manager.model.request.SignInRequest;
import com.task.manager.model.request.SignUpRequest;
import com.task.manager.model.response.JwtAuthenticationResponse;
import com.task.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final ConfirmationTokenService confirmationTokenService;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .userInfo(UserInfo.builder().firstName(request.getFirstName()).lastName(request.getLastName()).build())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        confirmationTokenService.sendConfirmationToken(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
