package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByPokemonId(int id);

    ReviewDto getReviewById(int reviewId, int pokemonId);
    ReviewDto updateDto(int pokemonId, int reviewId, ReviewDto reviewDto);

    void deleteReview(int pokemonId, int reviewId);
}
