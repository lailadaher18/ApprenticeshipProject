package com.apprenticeship.project.ConfidenceTrackerApp.repository;

import com.apprenticeship.project.ConfidenceTrackerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    @Query("SELECT u.latestStreak FROM User u WHERE u.userId = :userId")
    String findLatestStreakByUserId(Long userId);

    @Query("SELECT u.userToken FROM User u WHERE u.userId = :userId")
    String findUserTokenByUserId(Long userId);

    @Query("SELECT u.firstName FROM User u WHERE u.userId = :userId")
    String findFirstNameByUserId(Long userId);
}