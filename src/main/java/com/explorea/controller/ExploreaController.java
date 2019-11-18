package com.explorea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(path="/greeting")
public class ExploreaController {

    @GetMapping
    public Map<String,String> greeting(@RequestParam String name){
        return Collections.singletonMap("message", "Hello " + name
                + "! Server time is: " + new Date() + "!");
    }
}
