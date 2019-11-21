package com.explorea.controller;

import com.explorea.model.RouteDTO;
import com.explorea.TokenVerifier;
import com.explorea.VerifiedGoogleUserId;
import com.explorea.model.Route;
import com.explorea.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.StreamSupport.stream;

@Controller
@RequestMapping(path="/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @PostMapping
    public @ResponseBody
    ResponseEntity createRoute(@RequestHeader("authorization") String authString, @RequestBody Route route) {

        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(verifiedGoogleUserId.getHttpStatus());
        }

        if(routeRepository.save(route, verifiedGoogleUserId.getGoogleUserId())>0){
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public @ResponseBody Iterable<RouteDTO> getAllRoutes(
            @RequestParam(value = "cityname", required = false) String city,
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

        return foundRoutes;
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Optional<Route> getRoute(@PathVariable Integer id) {
        return Optional.ofNullable(routeRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteRoute(@PathVariable Integer id) {
        routeRepository.deleteById(id);
        return "Deleted " + id;
    }
}
