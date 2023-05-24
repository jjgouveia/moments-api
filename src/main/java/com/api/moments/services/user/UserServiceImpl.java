package com.api.moments.services.user;

import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.UserRepository;
import com.api.moments.services.follow.IFollowService;
import com.api.moments.services.security.IJwtService;
import com.api.moments.services.user.request.CreateUserRequest;
import com.api.moments.services.user.request.SimpleUpdateUserRequest;
import com.api.moments.services.user.request.UpdateUserRequest;
import com.api.moments.services.user.response.UserResponse;
import com.api.moments.util.validators.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    if (!Validators.isValidEmail(createUserRequest.getEmail()))
      throw new IllegalArgumentException("You should provide a valid email");

    if (!Validators.isValidPassword(createUserRequest.getPassword()))
      throw new IllegalArgumentException(
          "You should provide a password with at least 6 characters");

    if (!Validators.isValidUsername(createUserRequest.getUsername()))
      throw new IllegalArgumentException(
          "You should provide a username with at least 3 characters");

    if (this.existsByUsernameAndEmail(createUserRequest.getUsername(),
        createUserRequest.getEmail())) {
      throw new RuntimeException("Username or email already exists");
    }

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
    userResponse.setMoments(user.getMoments());

    return userResponse;
  }

  public User getUserById(UUID id) {
    return this.userRepository.findById(id).orElse(null);
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
      user1.setMoments(updateUserRequest.getMoments());
      return this.userRepository.save(user1);
    } else {
      throw new RuntimeException("User not found");
    }
  }

  @Override
  public void updateSimpleUser(String token, SimpleUpdateUserRequest updateUserRequest) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<User> user = this.userRepository.findById(userId);

    if (!userId.equals(user.<Object>map(User::getId).orElse(null)))
      throw new RuntimeException("You can't update another user");


    user.ifPresentOrElse(u -> {
      String email = Optional.ofNullable(updateUserRequest.getEmail()).orElse(u.getEmail());
      String username =
          Optional.ofNullable(updateUserRequest.getUsername()).orElse(u.getUsername());
      String password =
          Optional.ofNullable(updateUserRequest.getPassword()).map(passwordEncoder::encode)
              .orElse(u.getPassword());
      u.setEmail(email);
      u.setUsername(username);
      u.setPassword(password);
      this.userRepository.save(u);
    }, () -> {
      throw new UserNotFoundException("User not found");
    });
  }

  @Override
  public void updateUserMoments(UUID userId, UUID momentId) {
    Optional<User> user = this.userRepository.findById(userId);

    if (!userId.equals(user.<Object>map(User::getId).orElse(null)))
      throw new RuntimeException("You can't update another user");

    user.ifPresentOrElse(u -> {
      u.getMoments().add(momentId);
      this.userRepository.save(u);
    }, () -> {
      throw new UserNotFoundException("User not found");
    });
  }


  @Override
  public void addFollow(String followerToken, String username) {
    UUID followerId = jwtService.getUserId(followerToken);
    User follower = userRepository.findById(followerId)
        .orElseThrow(() -> new UserNotFoundException("Follower not found"));

    User followedUser = userRepository.findByUsername(username);
    if (followedUser == null) {
      throw new UserNotFoundException("Followed user not found");
    }

    if (followedUser.getId().equals(followerId)) {
      throw new InvalidFollowException(
          "Hey, self love is important, but you can't follow yourself here!");
    }

    if (followedUser.getFollowers().contains(followerId)) {
      throw new InvalidFollowException(
          "Ops, I know you love her/him, but you already follow this user!");
    }

    followedUser.getFollowers().add(followerId);
    follower.getFollowing().add(followedUser.getId());

    userRepository.save(followedUser);
    userRepository.save(follower);

    followService.saveFollow(followerId, followedUser.getId());
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
  public List<UserResponse> exploreUsersNotFollowed(String token) {
    UUID userId = this.jwtService.getUserId(token);
    System.out.println(userId);
    Optional<User> user = this.userRepository.findById(userId);
    System.out.println(user);

    if (user.isPresent()) {
      List<User> users = this.userRepository.findAll();
      List<UserResponse> usersResponse = new ArrayList<>();
      for (User u : users) {
        if (!u.getId().equals(userId) && !u.getFollowers().contains(userId)) {
          UserResponse userResponse = new UserResponse();
          userResponse.setId(u.getId());
          userResponse.setUsername(u.getUsername());
          userResponse.setEmail(u.getEmail());
          userResponse.setRole(u.getRole());
          userResponse.setFollowers(u.getFollowers());
          userResponse.setFollowing(u.getFollowing());
          userResponse.setMoments(u.getMoments());
          usersResponse.add(userResponse);
        }
      }
      return usersResponse;
    } else {
      throw new RuntimeException("User not found");
    }
  }

  @Override
  public boolean hasFollowed(String userId, String userIdToFollow) {
    return false;
  }
}
