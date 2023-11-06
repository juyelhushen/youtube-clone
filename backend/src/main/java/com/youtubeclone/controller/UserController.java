package com.youtubeclone.controller;

import com.youtubeclone.entity.User;
import com.youtubeclone.repository.UserRepository;
import com.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

//    @GetMapping("/info")
//    @ResponseStatus(HttpStatus.OK)
//    public String registerUser(Authentication authentication) {
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        userService.registerUser(jwt.getTokenValue());
//        return " User Info";
//    }

    @GetMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        userService.registerUser(jwt.getTokenValue());
        return "String";
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
