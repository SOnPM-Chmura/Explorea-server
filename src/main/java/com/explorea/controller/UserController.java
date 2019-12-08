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

import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public @ResponseBody
    ResponseEntity createUser(@RequestHeader("authorization") String authString) {

        VerifiedGoogleUserId verifiedGoogleUserId = TokenVerifier.getInstance().getGoogleUserId(authString);

        if(verifiedGoogleUserId.getHttpStatus() != HttpStatus.OK){
            return new ResponseEntity(verifiedGoogleUserId.getHttpStatus());
        }

        User user = new User();
        user.setGoogleUserId(verifiedGoogleUserId.getGoogleUserId());
        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);

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
