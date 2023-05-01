package com.api.moments.services.user;

import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;


  @Override
  public User create(CreateUserRequest createUserRequest) {
    User user = new User(createUserRequest.getUsername(), createUserRequest.getEmail(),
        createUserRequest.getPassword());

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

}
