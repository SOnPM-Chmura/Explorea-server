package com.explorea.controller;

import com.explorea.model.Route;
import com.explorea.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @PostMapping
    public @ResponseBody
    String createRoute(@RequestBody Route route) {
        routeRepository.save(route);
        return String.format("Added %s", route);
    }

    @GetMapping
    public @ResponseBody Iterable<Route> getAllRoutes(
//            @RequestParam("cityname") String city,
//            @RequestParam("time") Integer time,
//            @RequestParam("transport") String transport
    ) {

        return routeRepository.findAll();
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
