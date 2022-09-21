package com.gft.nutritionist.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gft.nutritionist.exception.TokenRefreshException;
import com.gft.nutritionist.model.NutritionistModel;
import com.gft.nutritionist.model.RefreshToken;
import com.gft.nutritionist.repository.NutritionistRepository;
import com.gft.nutritionist.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private Long refreshTokenDurationMs = 86400000L;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID nutritionistId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setNutritionist(nutritionistRepository.findById(nutritionistId).get());
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
    public int deleteByNutritionist(NutritionistModel nutritionist) {
        return refreshTokenRepository.deleteByNutritionist(nutritionist);
    }
}
