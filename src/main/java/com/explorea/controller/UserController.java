package com.explorea.controller;

import com.explorea.TokenVerifier;
import com.explorea.VerifiedGoogleUserId;
import com.explorea.model.User;
import com.explorea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public @ResponseBody ResponseEntity getAllUsers() {
        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity createUser(@RequestHeader("authorization") String authString) {

        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(Collections.singletonMap("id", "-1"), verifiedGoogleUserId.getHttpStatus());
        }

        User user = new User();
        user.setGoogleUserId(verifiedGoogleUserId.getGoogleUserId());

        Integer id = userRepository.save(user);
        if(id>0){
            return new ResponseEntity(Collections.singletonMap("id", id), HttpStatus.OK);
        }
        return new ResponseEntity(Collections.singletonMap("id", id), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
