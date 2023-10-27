package com.task.manager.config;

import com.task.manager.repository.ConfirmationTokenRepository;
import com.task.manager.repository.UserRepository;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Configuration
@Transactional
@EnableScheduling
public class DatabaseCleanupConfig {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    public DatabaseCleanupConfig(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void deleteNonVerifiedUsersAndExpiredTokens() {
        var confirmationTokens = confirmationTokenRepository.findAllByExpirationDateBefore(new Date(System.currentTimeMillis()));
        confirmationTokens.forEach((confirmationToken) ->
        {
            confirmationTokenRepository.delete(confirmationToken);
            if(!confirmationToken.getUser().isEnabled())
                userRepository.delete(confirmationToken.getUser());
        });
    }
}
