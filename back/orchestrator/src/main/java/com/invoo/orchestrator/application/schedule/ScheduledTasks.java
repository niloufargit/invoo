package com.invoo.orchestrator.application.schedule;

import com.invoo.orchestrator.domaine.repository.ITokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final ITokenRepository tokenRepository;

    public ScheduledTasks(ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(fixedRateString ="PT15M")
    public void cleanDbToken() {
        // This method is scheduled to run every minute to clean up expired and revoked tokens
        log.info("Cleaning database tokens...");
        log.info( "Scheduled method to clean up expired and revoked tokens" );
        tokenRepository.deleteTokenWhenExpiredIsTrueAndRevokedIsTrue();
    }
}
