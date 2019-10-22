package com.b.r.loteriab.r.Config;

import com.b.r.loteriab.r.Verify.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleConfiguration {
    @Autowired
    private VerificationRepository verificationRepository;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void purgeExpiredVerifications() {
        verificationRepository.deleteByExpirationDateBefore(new Date());
    }
}