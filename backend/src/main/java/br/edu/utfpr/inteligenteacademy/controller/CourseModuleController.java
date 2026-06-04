package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleSummaryDto;
import br.edu.utfpr.inteligenteacademy.service.CourseModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses/{courseId}/modules")
public class CourseModuleController {
    private final CourseModuleService courseModuleService;

    public CourseModuleController(CourseModuleService courseModuleService) {
        this.courseModuleService = courseModuleService;
    }

    // ----- GET -----
    @GetMapping("/{moduleId}")
    public ResponseEntity<CourseModuleResponseDto> findById(
            @PathVariable Long moduleId,
            @PathVariable Long courseId
    ){
        return ResponseEntity.ok(courseModuleService.findById(moduleId, courseId));
    }

    @GetMapping
    public ResponseEntity<List<CourseModuleSummaryDto>> findAllByCourseId(
            @PathVariable Long courseId
    ){
        return ResponseEntity.ok(courseModuleService.findAllByCourseId(courseId));
    }


    // ----- POST -----
    @PostMapping
    public ResponseEntity<CourseModuleResponseDto> save(
            @RequestBody @Valid CourseModuleCreationDto courseModuleCreationDto,
            @PathVariable Long courseId
    ){
        CourseModuleResponseDto courseModuleResponseDto = courseModuleService.save(
                courseModuleCreationDto, courseId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(courseModuleResponseDto);
    }
}
