package com.explorea.controller;

import com.explorea.ApiDirectionsDAO;
import com.explorea.EmptyJsonResponse;
import com.explorea.model.RouteDTO;
import com.explorea.TokenVerifier;
import com.explorea.VerifiedGoogleUserId;
import com.explorea.model.Route;
import com.explorea.model.SimpleDirectionsRoute;
import com.explorea.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path="/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ApiDirectionsDAO apiDirectionsDAO;

    @GetMapping("/directionsApi")
    public @ResponseBody ResponseEntity createRoute(@PathParam("encodedRoute") String encodedRoute) {

        SimpleDirectionsRoute simpleDirectionsRoute = apiDirectionsDAO.getSimpleDirectionsRoute(encodedRoute);

        if(simpleDirectionsRoute == null){
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(simpleDirectionsRoute, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity createRoute(@RequestHeader("authorization") String authString, @RequestBody Route route) {

        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(Collections.singletonMap("response", "ERROR"), verifiedGoogleUserId.getHttpStatus());
        }

        if(routeRepository.save(route, verifiedGoogleUserId.getGoogleUserId())>0){
            return new ResponseEntity(Collections.singletonMap("response", "SUCCESS"), HttpStatus.OK);
        }

        return new ResponseEntity(Collections.singletonMap("response", "ERROR"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public @ResponseBody ResponseEntity getAllRoutes(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "time", required = false) Integer time,
            @RequestParam(value = "transport", required = false) String transport
    ) {
        Iterable<RouteDTO> foundRoutes = routeRepository.findAll();
        foundRoutes = StreamSupport.stream(foundRoutes.spliterator(), false)
                .filter(StringUtils.isEmpty(city) ? p -> true : p -> city.equalsIgnoreCase(p.getCity()))
                .filter((StringUtils.isEmpty(transport) || time == null) ? p -> true :
                        (transport.equalsIgnoreCase("foot") ? p -> p.getTimeByFoot() <= time+5 :
                                transport.equalsIgnoreCase("bike") ? p -> p.getTimeByBike() <= time+5 : p -> true))
                .collect(Collectors.toList());

        return new ResponseEntity(foundRoutes, HttpStatus.OK);
    }

    @GetMapping("/created")
    public @ResponseBody ResponseEntity getRoutesCreatedByUser(@RequestHeader("authorization") String authString) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(verifiedGoogleUserId.getHttpStatus());
        }

        Iterable<RouteDTO> foundRoutes = routeRepository.findRoutesCreatedByUser(verifiedGoogleUserId.getGoogleUserId());
        return new ResponseEntity(foundRoutes, HttpStatus.OK);
    }

    @GetMapping("/favorite")
    public @ResponseBody ResponseEntity getUsersFavorite(@RequestHeader("authorization") String authString) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(verifiedGoogleUserId.getHttpStatus());
        }

        Iterable<RouteDTO> foundRoutes = routeRepository.findUsersFavorite(verifiedGoogleUserId.getGoogleUserId());
        return new ResponseEntity(foundRoutes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity getRoute(@PathVariable Integer id) {
        RouteDTO route = routeRepository.findById(id);

        if(route==null){
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);

        } else {
            return new ResponseEntity(route, HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity deleteRoute(@RequestHeader("authorization") String authString, @PathVariable Integer id) {
        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(Collections.singletonMap("response", "ERROR"), verifiedGoogleUserId.getHttpStatus());
        }

        if(routeRepository.deleteById(id, verifiedGoogleUserId.getGoogleUserId())>0){
            return new ResponseEntity(Collections.singletonMap("response", "SUCCESS"), HttpStatus.OK);
        }

        return new ResponseEntity(Collections.singletonMap("response", "ERROR"), HttpStatus.UNAUTHORIZED);
    }
}
