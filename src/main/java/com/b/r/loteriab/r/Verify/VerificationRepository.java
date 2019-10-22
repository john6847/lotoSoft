package com.b.r.loteriab.r.Verify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, String> {
    Optional<Verification> findByPhone(String phone);

    void deleteByPhone(String phone);
    void deleteByExpirationDateBefore(Date date);
}