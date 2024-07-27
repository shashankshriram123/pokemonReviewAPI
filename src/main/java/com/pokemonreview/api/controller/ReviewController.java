package com.pokemonreview.api.controller;

import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.model.Review;
import com.pokemonreview.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable("pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.createReview(pokemonId,reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable("pokemonId") int pokemonId){
        return reviewService.getReviewsByPokemonId(pokemonId);

    }

    @GetMapping("/pokemon/{pokemonId}/reveiews/{reviewId}")
    public ResponseEntity<ReviewDto> getReveiwById(@PathVariable("pokemonId") int pokemonId, @PathVariable("reviewId") int reviewId){
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reveiews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("pokemonId") int pokemonId, @PathVariable("reviewId") int reviewId, @RequestBody ReviewDto reviewDto){
        ReviewDto updatedReview = reviewService.updateDto(pokemonId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);

    }

    @DeleteMapping("/pokemon/{pokemonId}/reveiews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("pokemonId") int pokemonId, @PathVariable("reviewId") int reviewId){
        reviewService.deleteReview(pokemonId,reviewId);
        return new ResponseEntity<>("REVIEW DELETED SUCESSFULLY", HttpStatus.OK);
    }

}
