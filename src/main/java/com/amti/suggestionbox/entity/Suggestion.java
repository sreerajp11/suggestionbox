package com.amti.suggestionbox.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean isPublic = true;
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(length = 800)
    private String hrComment;

    private String department;
}
