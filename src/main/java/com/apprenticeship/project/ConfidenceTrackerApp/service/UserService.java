package com.apprenticeship.project.ConfidenceTrackerApp.service;

import com.apprenticeship.project.ConfidenceTrackerApp.model.User;
import com.apprenticeship.project.ConfidenceTrackerApp.repository.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(User user) {
        logger.debug("Registering user: {}", user.getUsername());

        if (user == null) {
            logger.error("User is null");
            throw new IllegalArgumentException("User cannot be null");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
      //  user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        logger.info("User saved successfully: {}", savedUser.getUsername());
        return savedUser;
    }

    public Optional<User> userLogin(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<User> editUser(Long userId, String username, String firstName, String password, String favoriteGenres) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            logger.error("User not found with ID: {}", userId);
            return Optional.empty();
        }

        User userToUpdate = userOptional.get();
        logger.debug("Editing user: {}", userToUpdate.getUsername());

        if (username != null) userToUpdate.setUsername(username);
        if (firstName != null) userToUpdate.setFirstName(firstName);
        if (password != null) userToUpdate.setPassword(passwordEncoder.encode(password));
        if (favoriteGenres != null) {
          //  userToUpdate.setFavoriteGenres(favoriteGenres);
            logger.info("Cache invalidated for userId: {}", userId);
        }

        User updatedUser = userRepository.save(userToUpdate);
        logger.info("User updated successfully: {}", updatedUser.getUsername());
        return Optional.of(updatedUser);
    }

    public Optional<User> getUser(Long userId) {
        logger.debug("Fetching user with ID: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            logger.error("User not found with ID: {}", userId);
        }
        return user;
    }
}