package com.api.moments.services.user;

import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.UserRepository;
import com.api.moments.services.follow.IFollowService;
import com.api.moments.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private IFollowService followService;


    @Override
    public User create(CreateUserRequest createUserRequest) {

        User user = new User(createUserRequest.getUsername(), createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public boolean existsByUsernameAndEmail(String username, String email) {
        return this.userRepository.existsByEmail(email) || this.userRepository.existsByUsername(
                username);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(String token, UpdateUserRequest updateUserRequest) {
        UUID userId = this.jwtService.getUserId(token);
        Optional<User> user = this.userRepository.findById(userId);

        if (user.isPresent()) {
            User user1 = user.get();
            user1.setUsername(updateUserRequest.getUsername());
            user1.setEmail(updateUserRequest.getEmail());
            user1.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            user1.setFollowers(updateUserRequest.getFollowers());
            user1.setFollowing(updateUserRequest.getFollowing());
            return this.userRepository.save(user1);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void addFollow(String followerToken, String username) {
        UUID followerId = this.jwtService.getUserId(followerToken);
        Optional<User> follower = this.userRepository.findById(followerId);
        Optional<User> followedUser = Optional.ofNullable(this.userRepository.findByUsername(username));

        if (followedUser.isPresent() && followerId.equals(followedUser.get().getId())) {
            throw new RuntimeException("Hey, self love is important, but you can't follow yourself here!");
        }

        if (followedUser.isPresent() && follower.isPresent()) {
            User userIWantToFollow = followedUser.get();
            User userThatFollows = follower.get();
            if (!userIWantToFollow.getFollowers().contains(followerId)) {
                userThatFollows.getFollowing().add(userIWantToFollow.getId());
                userIWantToFollow.getFollowers().add(followerId);
                this.userRepository.save(userIWantToFollow);
                this.userRepository.save(userThatFollows);
                this.followService.saveFollow(followerId, userIWantToFollow.getId());
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void removeFollow(String token, String followId) {

    }

    @Override
    public boolean hasFollowed(String userId, String userIdToFollow) {
        return false;
    }
}
