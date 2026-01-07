package com.roadsidehelp.api.feature.auth.service;

import com.roadsidehelp.api.core.constants.TimeZones;
import com.roadsidehelp.api.feature.auth.entity.UserAccount;
import com.roadsidehelp.api.feature.auth.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCleanupService {

    private final UserAccountRepository userRepo;

    // Runs every 10 minutes
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void removeUnverifiedUsers() {

        OffsetDateTime now =
                OffsetDateTime.now(ZoneId.of(TimeZones.INDIA));

        List<UserAccount> expiredUsers =
                userRepo.findByIsVerifiedFalseAndTokenExpirationBefore(now);

        if (!expiredUsers.isEmpty()) {
            userRepo.deleteAll(expiredUsers);
            log.info(
                    "Deleted {} unverified expired user accounts",
                    expiredUsers.size()
            );
        }
    }
}
