package com.pokemonreview.api.service.impl;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.exceptions.PokemonNotFoundException;
import com.pokemonreview.api.exceptions.ReviewNotFoundException;
import com.pokemonreview.api.model.Pokemon;
import com.pokemonreview.api.model.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.ReviewService;
import org.aspectj.weaver.reflect.IReflectionWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("POKEMON WITH ASSOCIATED REVIEW NOT FOUND"));

        review.setPokemon(pokemon);
        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);

    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {
        List<Review> reviews = reviewRepository.findByPokemonId(id);
        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("POKEMON WITH ASSOCIATED REVIEW NOT FOUND"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("REVIEW WITH ASSOCIATED POKEMON NOT FOUND"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("THIS REVIEW DOES NOT BELONG TO A POKEMON");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateDto(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("POKEMON WITH ASSOCIATED REVIEW NOT FOUND"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("REVIEW WITH ASSOCIATED POKEMON NOT FOUND"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("THIS REVIEW DOES NOT BELONG TO A POKEMON");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);

    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("POKEMON WITH ASSOCIATED REVIEW NOT FOUND"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("REVIEW WITH ASSOCIATED POKEMON NOT FOUND"));
        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("THIS REVIEW DOES NOT BELONG TO A POKEMON");
        }
        reviewRepository.delete(review);
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(reviewDto.getContent());
        reviewDto.setStars(reviewDto.getStars());


        return reviewDto;
    }
}

