package com.explorea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(path="/greeting")
public class ExploreaController {

    @GetMapping
    public @ResponseBody Map<String,String> greeting(@PathParam("name") String name){
        return Collections.singletonMap("message", "Hello " + name
                + "! Server time is: " + new Date() + "!");
    }

    @PostMapping
    public @ResponseBody Map<String,String> postGreeting(@RequestBody Map<String,String> name){
        return Collections.singletonMap("message", "Hello " + name.get("name")
                + "! Server time is: " + new Date() + "!");
    }
}
