package com.explorea.controller;

import com.explorea.TokenVerifier;
import com.explorea.VerifiedGoogleUserId;
import com.explorea.model.User;
import com.explorea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {

    private static final String CLIENT_ID_1 =
            "685250076671-fm3omtp4u6cj7uubb5crutdhlhdtapos.apps.googleusercontent.com";
    private static final String CLIENT_ID_2 =
            "685250076671-b8fa2201uknafpkskc10surqgq3dpqdt.apps.googleusercontent.com";
    private static final String CLIENT_ID_3 =
            "685250076671-9l423p4utl2atj5pq1iqm4p27aemce86.apps.googleusercontent.com";
    private static final String CLIENT_ID_4 =
            "685250076671-v2uk6ii19acgok6n2fa8jmog7uqsfamq.apps.googleusercontent.com";
    private static final String CLIENT_ID_5 =
            "685250076671-ohdnuls4fhg6d9cnkpb4g8p86up9pnki.apps.googleusercontent.com";

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
