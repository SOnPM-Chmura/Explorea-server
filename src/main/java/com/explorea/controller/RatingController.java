package com.explorea.controller;

import com.explorea.TokenVerifier;
import com.explorea.VerifiedGoogleUserId;
import com.explorea.model.Rating;
import com.explorea.model.RatingDTO;
import com.explorea.repository.RatingRepository;
import com.explorea.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RouteRepository routeRepository;

    @PostMapping
    public @ResponseBody
    ResponseEntity createRating(@RequestHeader("authorization") String authString, @RequestBody RatingDTO rating) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(verifiedGoogleUserId.getHttpStatus());
        }

        if(ratingRepository.save(rating, verifiedGoogleUserId.getGoogleUserId())<=0){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        if(routeRepository.updateAvgRating(rating.getRouteId())>0){
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

//    @GetMapping
//    public @ResponseBody Iterable<Rating> getAllRatings() {
//        return ratingRepository.findAll();
//    }

    @GetMapping()
    public @ResponseBody Optional<RatingDTO> getRatingByUserAndRoute(@RequestHeader("authorization") String authString,
                                                                     @RequestParam(value = "routeId") Integer routeId) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return null;
        }
        return Optional.ofNullable(ratingRepository.findByUserAndRoute(routeId, verifiedGoogleUserId.getGoogleUserId()));
    }


    @DeleteMapping("/{id}")
    public @ResponseBody String deleteRating(@PathVariable Integer id) {
        ratingRepository.deleteById(id);
        return "Deleted " + id;
    }
}
