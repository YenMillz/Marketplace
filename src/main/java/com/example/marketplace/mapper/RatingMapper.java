package com.example.marketplace.mapper;

import com.example.marketplace.model.Rating;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RatingMapper {
    Rating findRatingById(Long userId, Long productId);

    void createRating(Long userId, Long productId, Boolean like);

    void updateRating(Rating rating);
}
