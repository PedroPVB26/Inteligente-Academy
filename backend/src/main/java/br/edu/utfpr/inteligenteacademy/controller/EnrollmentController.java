package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.model.dto.enrollment.EnrollmentResponseDto;
import br.edu.utfpr.inteligenteacademy.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@AllArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.findAll());
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<EnrollmentResponseDto> findById(@PathVariable Long enrollmentId){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.findById(enrollmentId));
    }

    // Todos os cursos em que um determinado usuário está inscrito
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<EnrollmentResponseDto>> findByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.findByUser(userId));
    }

    // Todas as matrículas em um determinado curso
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDto>> findByCourseId(@PathVariable Long courseId){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.findByCourse(courseId));
    }
}
