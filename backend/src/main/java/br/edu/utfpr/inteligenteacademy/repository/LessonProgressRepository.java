package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    /**
     * Busca o progresso de uma aula específica de uma matrícula.
     *
     * Uso:
     * - Abrir uma aula.
     * - Atualizar o progresso de uma aula.
     * - Verificar se já existe progresso registrado.
     */
    Optional<LessonProgress> findByEnrollmentAndLesson(
            Enrollment enrollment,
            Lesson lesson
    );

    /**
     * Busca todos os progressos de uma matrícula.
     *
     * Uso:
     * - Montar a tela do curso.
     * - Calcular progresso dos módulos.
     * - Calcular progresso geral do curso.
     */
    List<LessonProgress> findByEnrollmentId(Long enrollmentId);

    /**
     * Verifica se já existe progresso para uma aula específica.
     *
     * Uso:
     * - Antes de criar um LessonProgress.
     * - Evitar registros duplicados.
     *
     * Observação:
     * A UniqueConstraint já protege o banco,
     * mas este método pode evitar exceções.
     */
    boolean existsByEnrollmentAndLesson(
            Enrollment enrollment,
            Lesson lesson
    );

    /**
     * Conta quantas aulas já foram concluídas
     * dentro de uma matrícula.
     *
     * Uso:
     * - Recalcular Enrollment.progressPercentage.
     * - Exibir "15 de 20 aulas concluídas".
     */
    long countByEnrollmentIdAndCompletedTrue(
            Long enrollmentId
    );

    @Query("""
    SELECT COUNT(lp)
    FROM LessonProgress lp
    WHERE lp.enrollment.id = :enrollmentId
      AND lp.lesson.courseModule.course.id = :courseId
      AND lp.completed = true
""")
    long countCompletedLessonsByEnrollmentAndCourse(
            Long enrollmentId,
            Long courseId
    );



    /**
     * Retorna todos os progressos da matrícula
     * carregando também Lesson e CourseModule.
     *
     * Uso:
     * - Tela principal do curso.
     * - Exibição dos módulos com seus percentuais.
     *
     * Evita o problema de N+1 queries.
     */
    @Query("""
        SELECT lp
        FROM LessonProgress lp
        JOIN FETCH lp.lesson l
        JOIN FETCH l.courseModule
        WHERE lp.enrollment.id = :enrollmentId
    """)
    List<LessonProgress> findAllWithLessonAndModuleByEnrollmentId(
            Long enrollmentId
    );

    Optional<LessonProgress> findByEnrollmentUserIdAndLessonId(
            Long userId,
            Long lessonId
    );
}