package com.youtubeclone.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtubeclone.entity.User;
import com.youtubeclone.payload.UserInfoResponse;
import com.youtubeclone.payload.UserRequest;
import com.youtubeclone.payload.UserResponse;
import com.youtubeclone.payload.VideoResponse;
import com.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    @Value("${spring.security.oauth2.authorizationserver.endpoint.oidc.user-info-uri}")
    private String userInfoUrl;
    private final UserRepository userRepository;
    private final S3Service s3Service;


    @Override
    public String registerUser(String tokenValue) {

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

            Optional<User> userBySubject = userRepository.findUserBySub(userInfoResponse.getSub());

            if (userBySubject.isPresent()) {
                return userBySubject.get().getId();
            } else {
                User user = User.builder()
                        .firstName(userInfoResponse.getGivenName())
                        .lastName(userInfoResponse.getNickName())
                        .fullName(userInfoResponse.getName())
                        .sub(userInfoResponse.getSub())
                        .email(userInfoResponse.getEmail())
                        .build();
                return userRepository.save(user).getId();

            }


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

    @Override
    public void addToLikedComment(String commentId) {
        User curretUser = getCurrentUser();
        curretUser.addToLikedComment(commentId);
        userRepository.save(curretUser);
    }

    @Override
    public void addToDisLikedComment(String commentId) {
        User currentUser = getCurrentUser();
        currentUser.addToDisLikedComment(commentId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromLikedComment(String commentId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromLikedComment(commentId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromDisLikedComment(String commentId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromDisLikedComment(commentId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedVideo(String videoId) {
        return getCurrentUser().getLikedVideos().stream()
                .anyMatch(likeVideos -> likeVideos.equals(videoId));
    }

    @Override
    public boolean ifDisLikedVideo(String videoId) {
        return getCurrentUser().getDisLikedVideos().stream()
                .anyMatch(dislikeVideos -> dislikeVideos.equals(videoId));
    }

    @Override
    public boolean ifLikedComment(String commentId) {
        return getCurrentUser().getLikedComment().stream().anyMatch(
                likedComment -> likedComment.equals(commentId)
        );
    }

    @Override
    public boolean ifDisLikedComment(String commentId) {
        return getCurrentUser().getDisLikedComment().stream().anyMatch(
                disLikedComment -> disLikedComment.equals(commentId)
        );
    }


    @Override
    public void addVideoToHistory(String videoId) {
        User user = getCurrentUser();
        user.addVideoToHistory(videoId);
        userRepository.save(user);
    }

    @Override
    public Boolean subscribeToUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.addSubscribedToUser(userId);

        User user = getUser(userId);
        user.addSubscriber(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean unSubscribeToUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromSubscribedToUser(userId);

        User user = getUser(userId);
        user.removeSubscriber(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(user);
        return true;
    }

    @Override
    public Set<String> getUserHistory(String userId) {
        User user = getUser(userId);
        return user.getVideoHistory();

    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id - " + userId));
    }

    @Override
    public String uploadProfile(String userId, MultipartFile file) {
        User user = getUser(userId);
        String profileUrl = s3Service.uploadFile(file);
        user.setProfileUrl(profileUrl);
        userRepository.save(user);
        return profileUrl;
    }

    @Override
    public UserResponse getUserResponse(String userId) {
        User user = getUser(userId);
        return userResponse(user);
    }

    //    @Override
//    public Set<VideoResponse> getUserAllLikedVideos(String userId) {
//        User user = getUser(userId);
//        Set<String> videoIds = user.getLikedVideos();
//        return videoService.getUserAllLikedVideos(videoIds);
//    }

    private UserResponse userResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .channelName(user.getChannelName())
                .channelDescription(user.getChannelDescription())
                .profileUrl(user.getProfileUrl())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public String patchUserUpdate(String userId, UserRequest request) {
        User user = getUser(userId);
        String message = "";
        if (user != null) {
            if (request.getChannelName() != null) {
                user.setChannelName(request.getChannelName());
                message = "Channel name changed successfully";
            }

            if (request.getChannelDescription() != null) {
                user.setChannelDescription(request.getChannelDescription());
                message = "Channel description updated successfully";
            }

            userRepository.save(user);
            return message;
        }
        throw new IllegalArgumentException("User not found");
    }



}
