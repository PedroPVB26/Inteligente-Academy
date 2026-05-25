package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.model.dto.curso.CursoCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.curso.CursoResponseDto;
import br.edu.utfpr.inteligenteacademy.service.CursoService;


@RestController
@RequestMapping("/curso")
public class CursoController {
    private CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // ----- GET -----
    @GetMapping
    public ResponseEntity<List<CursoResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.findAll());
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<CursoResponseDto> findById(@PathVariable Long cursoId) {
        CursoResponseDto cursoResponseDto = cursoService.findById(cursoId);
        return ResponseEntity.status(HttpStatus.OK).body(cursoResponseDto);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<CursoResponseDto> save(@RequestBody CursoCreationDto cursoCreationDto) {
        CursoResponseDto CursoSalvo = cursoService.save(cursoCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CursoSalvo);
    }

    // ----- PUT -----

    // ----- DELETE -----

}