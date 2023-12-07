package com.youtubeclone.controller;

import com.youtubeclone.entity.User;
import com.youtubeclone.payload.VideoResponse;
import com.youtubeclone.repository.UserRepository;
import com.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId =  userService.registerUser(jwt.getTokenValue());
        return userId;
    }

    @PostMapping("subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean subscribeToUser(@PathVariable String userId) {
       return userService.subscribeToUser(userId);
    }

    @PostMapping("unsubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean unSubscribeToUser(@PathVariable String userId) {
        return userService.unSubscribeToUser(userId);

    }

    @GetMapping("/history/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getAllUserHistory(@PathVariable String userId) {
        return userService.getUserHistory(userId);
    }





    @GetMapping("all")
    public List<User> userList() {
        return userRepository.findAll();
    }

    @DeleteMapping("deleteallUser")
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
