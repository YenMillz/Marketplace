package com.example.marketplace.service;

import com.example.marketplace.exception.TokenRefreshException;
import com.example.marketplace.mapper.RefreshTokenMapper;
import com.example.marketplace.mapper.UserMapper;
import com.example.marketplace.model.RefreshToken;
import com.example.marketplace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${marketplace.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Autowired
    private UserMapper userMapper;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenMapper.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        User user = userMapper.findById(userId);
        refreshToken.setUserId(user.getId());
        /*refreshToken.setUsername(user.getUsername());*/
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenMapper.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenMapper.delete(token.getUserId());
            throw new TokenRefreshException("Refresh token was expired. Please make a new login request");
        }

        return token;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenMapper.delete(userId);
    }
}
