package com.task.manager.service;

import com.task.manager.model.User;
import com.task.manager.repository.ConfirmationTokenRepository;
import com.task.manager.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public UserService(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public ResponseEntity<?> confirmEmail(String token) {
        var confirmationToken = confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
        User user = confirmationToken.orElseThrow().getUser();
        if(user.isEnabled()) return ResponseEntity.ok("email already verified");
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok("email successfully verified");
    }



}
