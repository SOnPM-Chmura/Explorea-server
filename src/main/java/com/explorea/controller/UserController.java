package com.explorea.controller;

import com.explorea.model.User;
import com.explorea.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {

    private static final String CLIENT_ID_1 =
            "";
    private static final String CLIENT_ID_2 =
            "";
    private static final String CLIENT_ID_3 =
            "";
    private static final String CLIENT_ID_4 =
            "";
    private static final String CLIENT_ID_5 =
            "";

    @Autowired
    private UserRepository userRepository;

//    @PostMapping
//    public @ResponseBody
//    String createUser(@RequestBody User user) {
//        userRepository.save(user);
//        return String.format("Added %s", user);
//    }

    @PostMapping
    public @ResponseBody
    ResponseEntity createUser(@RequestHeader("authorization") String authString) {

        authString = authString.replace("Bearer ","");
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3, CLIENT_ID_4, CLIENT_ID_5))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(authString);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            User user = new User();
            user.setGoogleUserId(userId);

            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);

        } else {
            System.out.println("Invalid ID token.");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<User> getUser(@PathVariable Integer id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return "Deleted " + id;
    }
}
