package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.model.dto.enrollment.EnrollmentRequestCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.enrollment.EnrollmentRequestResponseDto;
import br.edu.utfpr.inteligenteacademy.service.EnrollmentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment-requests")
@RequiredArgsConstructor
public class EnrollmentRequestController {
    private final EnrollmentRequestService enrollmentRequestService;


    // ----- GET -----
    @GetMapping
    public ResponseEntity<List<EnrollmentRequestResponseDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentRequestService.findAll());
    }

    @GetMapping("/{enrollmentRequestId}")
    public ResponseEntity<EnrollmentRequestResponseDto> findById(@PathVariable Long enrollmentRequestId){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentRequestService.findById(enrollmentRequestId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EnrollmentRequestResponseDto>> findPending() {
        return ResponseEntity.ok(
                enrollmentRequestService.findPending()
        );
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<EnrollmentRequestResponseDto> enrollmentRequest(
            @RequestBody EnrollmentRequestCreationDto enrollmentRequestCreationDto,
            @AuthenticationPrincipal User user
    ){
        EnrollmentRequestResponseDto  enrollmentRequestResponseDto = enrollmentRequestService.createEnrollmentRequest(
                user.getId(), enrollmentRequestCreationDto.courseId()
        );

        return  ResponseEntity.status(HttpStatus.CREATED).body(enrollmentRequestResponseDto);
    }

    // ----- PATCH -----
    @PatchMapping("/{enrollmentRequestId}/approve")
    public ResponseEntity<EnrollmentRequestResponseDto> approve(
            @PathVariable Long enrollmentRequestId
    ) {
        return ResponseEntity.ok(
                enrollmentRequestService.approve(
                        enrollmentRequestId
                )
        );
    }

    @PatchMapping("/{enrollmentRequestId}/reject")
    public ResponseEntity<EnrollmentRequestResponseDto> reject(
            @PathVariable Long enrollmentRequestId
    ) {
        return ResponseEntity.ok(
                enrollmentRequestService.reject(
                        enrollmentRequestId
                )
        );
    }
}

