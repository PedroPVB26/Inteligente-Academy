package br.edu.utfpr.inteligenteacademy.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    private Boolean completed;

    private LocalDateTime completedAt;
}