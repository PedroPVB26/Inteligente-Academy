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

import br.edu.utfpr.inteligenteacademy.model.dto.CursoResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.CursoCreationDto;
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

    @GetMapping("/{CursoId}")
    public ResponseEntity<CursoResponseDto> findById(@PathVariable Integer CursoId) {
        CursoResponseDto CursoResponseDto = cursoService.findById(CursoId);
        return ResponseEntity.status(HttpStatus.OK).body(CursoResponseDto);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<CursoResponseDto> save(@RequestBody CursoCreationDto CursoCreationDto) {
        CursoResponseDto CursoSalvo = cursoService.save(CursoCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CursoSalvo);
    }

    // ----- PUT -----

    // ----- DELETE -----

}