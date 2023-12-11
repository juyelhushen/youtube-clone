package com.youtubeclone.controller;

import com.youtubeclone.entity.User;
import com.youtubeclone.payload.UserInfoResponse;
import com.youtubeclone.payload.UserRequest;
import com.youtubeclone.payload.UserResponse;
import com.youtubeclone.payload.VideoResponse;
import com.youtubeclone.repository.UserRepository;
import com.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return userService.registerUser(jwt.getTokenValue());
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

    @GetMapping("history/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getAllUserHistory(@PathVariable String userId) {
        return userService.getUserHistory(userId);
    }

    @PutMapping("upload/profile/{userId}")
    public String uploadProfile(@PathVariable String userId,@RequestParam("file") MultipartFile file) {
        return userService.uploadProfile(userId, file);
    }

    @GetMapping("{userId}")
    public UserResponse getUserResponse(@PathVariable String userId) {
        return userService.getUserResponse(userId);
    }

    @PatchMapping("patch/update/{userId}")
    public String patchUserUpdate(@PathVariable String userId, @RequestBody UserRequest request) {
        return userService.patchUserUpdate(userId, request);
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
