package br.edu.utfpr.inteligenteacademy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
    name = "lesson_progress",
    uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_lesson_progress_enrollment_lesson",
                columnNames = {"enrollment_id", "lesson_id"}
        )
    },
    indexes = {
        @Index(
                name = "idx_lesson_progress_enrollment",
                columnList = "enrollment_id"
        )
    }
)
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    /**
     * Maior posição já alcançada pelo aluno no vídeo.
     *
     * Exemplo:
     * 0 -> 300 -> 100 -> 250
     *
     * Valor armazenado:
     * 300
     */
    @Column(nullable = false)
    private Long highestPositionReachedSeconds = 0L;

    /**
     * Indica se a aula foi concluída.
     *
     * Regra sugerida:
     * highestPositionReachedSeconds >=
     * lesson.durationInSeconds * 0.95
     */
    @Column(nullable = false)
    private Boolean completed = false;

    /**
     * Momento em que o aluno iniciou a aula pela primeira vez.
     */
    @Column(nullable = false)
    private Instant startedAt;

    /**
     * Momento em que a aula foi concluída.
     */
    private Instant completedAt;

    /**
     * Último acesso à aula.
     */
    @Column(nullable = false)
    private Instant lastAccessedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}