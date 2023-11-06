package com.youtubeclone.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtubeclone.entity.User;
import com.youtubeclone.payload.UserInfoResponse;
import com.youtubeclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserServiceImp implements UserService {

    @Value("${spring.security.oauth2.authorizationserver.endpoint.oidc.user-info-uri}")
    private String userInfoUrl;
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(String tokenValue) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoUrl))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoResponse userInfoResponse = mapper.readValue(body, UserInfoResponse.class);

            User user = User.builder()
                    .firstName(userInfoResponse.getGivenName())
                    .lastName(userInfoResponse.getNickName())
                    .fullName(userInfoResponse.getName())
                    .sub(userInfoResponse.getSub())
                    .email(userInfoResponse.getEmail())
                    .build();
            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException("Exception Occurred while registering user", e);
        }
    }

    public User getCurrentUser() {
        String sub = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("sub");
        return userRepository.findUserBySub(sub)
                .orElseThrow(() -> new IllegalArgumentException("No record available with sub - " + sub));
    }

    @Override
    public void addToLikedVideo(String videoId) {
        User curretUser = getCurrentUser();
        curretUser.addToLikedVideos(videoId);
        userRepository.save(curretUser);
    }

    @Override
    public void addToDisLikedVideo(String videoId) {
        User curretUser = getCurrentUser();
        curretUser.addToDisLikedVideos(videoId);
        userRepository.save(curretUser);
    }

    @Override
    public void removeFromLikedVideos(String videoId) {
        User curretUser = getCurrentUser();
        curretUser.removeFromLikedVideos(videoId);
        userRepository.save(curretUser);

    }

    @Override
    public void removeFromDisLikedVideos(String videoId) {
        User curretUser = getCurrentUser();
        curretUser.removeFromDisLikedVideos(videoId);
        userRepository.save(curretUser);
    }

    public boolean ifLikedVideos(String videoId) {
        return getCurrentUser().getLikedVideos().stream()
                .anyMatch(likeVideos -> likeVideos.equals(videoId));
    }

    public boolean ifDisLikedVideos(String videoId) {
        return getCurrentUser().getDisLikedVideos().stream()
                .anyMatch(dislikeVideos -> dislikeVideos.equals(videoId));
    }

    @Override
    public void addVideoToHistory(String videoId) {
        User user = getCurrentUser();
        user.addVideoToHistory(videoId);
        userRepository.save(user);
    }
}
