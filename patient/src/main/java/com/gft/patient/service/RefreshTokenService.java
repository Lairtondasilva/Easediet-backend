package com.gft.patient.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gft.patient.exception.TokenRefreshException;
import com.gft.patient.models.PatientModel;
import com.gft.patient.models.RefreshToken;
import com.gft.patient.repositories.PatientRepository;
import com.gft.patient.repositories.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private Long refreshTokenDurationMs = 86400000L;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID patientId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setPatient(patientRepository.findById(patientId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByPatient(PatientModel patient) {
        return refreshTokenRepository.deleteByPatient(patient);
    }
}
