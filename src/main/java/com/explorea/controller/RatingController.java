package com.explorea.controller;

import com.explorea.model.Rating;
import com.explorea.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @PostMapping
    public @ResponseBody
    String createRating(@RequestBody Rating rating) {
        ratingRepository.save(rating);
        return String.format("Added %s", rating);
    }

    @GetMapping
    public @ResponseBody Iterable<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Rating> getRating(@PathVariable Integer id) {
        return Optional.ofNullable(ratingRepository.findById(id));
    }


    @DeleteMapping("/{id}")
    public @ResponseBody String deleteRating(@PathVariable Integer id) {
        ratingRepository.deleteById(id);
        return "Deleted " + id;
    }
}
