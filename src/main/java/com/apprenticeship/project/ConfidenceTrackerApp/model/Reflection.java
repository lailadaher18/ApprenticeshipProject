package com.apprenticeship.project.ConfidenceTrackerApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    public class Reflection {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long reflectionId;
        private Long userId;
        private String reflectionText;
        private String confidenceRating;
        private String emoji;
        private LocalDateTime createdAt;

    }