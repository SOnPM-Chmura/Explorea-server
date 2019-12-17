package com.explorea.controller;

import com.explorea.EmptyJsonResponse;
import com.explorea.TokenVerifier;
import com.explorea.VerifiedGoogleUserId;
import com.explorea.model.Rating;
import com.explorea.model.RatingDTO;
import com.explorea.model.RouteDTO;
import com.explorea.repository.RatingRepository;
import com.explorea.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path="/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping("/all")
    public @ResponseBody ResponseEntity getAllRatings() {
        return new ResponseEntity(ratingRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity createRating(@RequestHeader("authorization") String authString, @RequestBody RatingDTO rating) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(Collections.singletonMap("id", -1), verifiedGoogleUserId.getHttpStatus());
        }

        Integer id = ratingRepository.saveAndUpdateAvgRating(rating, verifiedGoogleUserId.getGoogleUserId());

        if(id<=0){
            return new ResponseEntity(Collections.singletonMap("id", id), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(Collections.singletonMap("id", id), HttpStatus.OK);
    }

    @GetMapping()
    public @ResponseBody ResponseEntity getRatingByUserAndRoute(@RequestHeader("authorization") String authString,
                                                                     @RequestParam(value = "routeId") Integer routeId) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(new EmptyJsonResponse(), verifiedGoogleUserId.getHttpStatus());
        }

        RatingDTO rating = ratingRepository.findByUserAndRoute(routeId, verifiedGoogleUserId.getGoogleUserId());

        if(rating==null){
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);

        } else {
            return new ResponseEntity(rating, HttpStatus.OK);
        }
    }
}
