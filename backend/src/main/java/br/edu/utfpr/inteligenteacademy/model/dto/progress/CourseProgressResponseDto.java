package br.edu.utfpr.inteligenteacademy.model.dto.progress;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CourseProgressResponse", description = "Represents a user's progress across an entire course")
public record CourseProgressResponseDto(
        @Schema(description = "User id", example = "12")
        Long userId,

        @Schema(description = "User full name", example = "João Silva")
        String userName,

        @Schema(description = "Course id", example = "5")
        Long courseId,

        @Schema(description = "Course name", example = "Introdução à Java")
        String courseName,

        @Schema(description = "Progress relative to the whole course (0.0 - 1.0)", example = "0.1")
        Double progress
){

}

