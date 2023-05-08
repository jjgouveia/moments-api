package com.api.moments.services.user;

import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.UserRepository;
import com.api.moments.services.follow.IFollowService;
import com.api.moments.services.security.IJwtService;
import com.api.moments.services.user.request.CreateUserRequest;
import com.api.moments.services.user.request.UpdateUserRequest;
import com.api.moments.services.user.response.UserResponse;
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

  @Override
  public UserResponse getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new RuntimeException("User not found");
    }

    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setUsername(user.getUsername());
    userResponse.setEmail(user.getEmail());
    userResponse.setRole(user.getRole());
    userResponse.setFollowers(user.getFollowers());
    userResponse.setFollowing(user.getFollowing());

    return userResponse;
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
      throw new RuntimeException(
          "Hey, self love is important, but you can't follow yourself here!");
    }

    if (followedUser.isPresent() && followedUser.get().getFollowers().contains(followerId)) {
      throw new RuntimeException("Ops, I know you love her/him, but you already follow this user!");
    }

    if (followedUser.isPresent() && follower.isPresent()) {
      User userIWantToFollow = followedUser.get();
      User userThatFollows = follower.get();
      userThatFollows.getFollowing().add(userIWantToFollow.getId());
      userIWantToFollow.getFollowers().add(followerId);
      this.userRepository.save(userIWantToFollow);
      this.userRepository.save(userThatFollows);
      this.followService.saveFollow(followerId, userIWantToFollow.getId());

    } else {
      throw new RuntimeException("User not found");
    }
  }

  @Override
  public void removeFollow(String followerToken, String username) {
    UUID followerId = this.jwtService.getUserId(followerToken);
    Optional<User> follower = this.userRepository.findById(followerId);
    Optional<User> followedUser = Optional.ofNullable(this.userRepository.findByUsername(username));

    if (followedUser.isPresent() && followerId.equals(followedUser.get().getId())) {
      throw new RuntimeException(
          "Hey, I do not to be rude, but you can't unfollow yourself here, ok? Not even in real life!");
    }

    if (followedUser.isPresent() && !followedUser.get().getFollowers().contains(followerId)) {
      throw new RuntimeException(
          "Ops, I comprehend that you don't like this user, but you can't unfollow someone you don't even follow!");
    }

    if (followedUser.isPresent() && follower.isPresent()) {
      User userIWantToFollow = followedUser.get();
      User userThatFollows = follower.get();
      if (userIWantToFollow.getFollowers().contains(followerId)) {
        userThatFollows.getFollowing().remove(userIWantToFollow.getId());
        userIWantToFollow.getFollowers().remove(followerId);
        this.followService.deleteFollow(followerId, userIWantToFollow.getId());
        this.userRepository.save(userIWantToFollow);
        this.userRepository.save(userThatFollows);
      }
    } else {
      throw new RuntimeException("User not found");
    }

  }

  @Override
  public boolean hasFollowed(String userId, String userIdToFollow) {
    return false;
  }
}
