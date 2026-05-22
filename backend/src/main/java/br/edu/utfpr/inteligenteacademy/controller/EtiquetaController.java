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

import br.edu.utfpr.inteligenteacademy.model.dto.etiqueta.EtiquetaCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.etiqueta.EtiquetaResponseDto;
import br.edu.utfpr.inteligenteacademy.service.EtiquetaService;


@RestController
@RequestMapping("/etiqueta")
public class EtiquetaController {
    private EtiquetaService etiquetaService;

    public EtiquetaController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    // ----- GET -----
    @GetMapping
    public ResponseEntity<List<EtiquetaResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(etiquetaService.findAll());
    }

    @GetMapping("/{etiquetaId}")
    public ResponseEntity<EtiquetaResponseDto> findById(@PathVariable Integer etiquetaId) {
        EtiquetaResponseDto etiquetaResponseDto = etiquetaService.findById(etiquetaId);
        return ResponseEntity.status(HttpStatus.OK).body(etiquetaResponseDto);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<EtiquetaResponseDto> save(@RequestBody EtiquetaCreationDto etiquetaCreationDto) {
        EtiquetaResponseDto etiquetaSalvo = etiquetaService.save(etiquetaCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(etiquetaSalvo);
    }

    // ----- PUT -----

    // ----- DELETE -----

}