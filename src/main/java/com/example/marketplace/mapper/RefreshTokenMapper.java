package com.example.marketplace.mapper;

import com.example.marketplace.model.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    Optional<RefreshToken> findByToken(String token);

    void save(RefreshToken refreshToken);

    void delete(Long userId);
}
