package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.model.dto.tag.TagCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.tag.TagResponseDto;
import br.edu.utfpr.inteligenteacademy.service.TagService;


@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // ----- GET -----
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findAll());
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagResponseDto> findById(@PathVariable Long tagId) {
        TagResponseDto tagResponseDto = tagService.findById(tagId);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDto);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<TagResponseDto> save(@RequestBody @Valid TagCreationDto tagCreationDto) {
        TagResponseDto savedTag = tagService.save(tagCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
    }

    // ----- PUT -----

    // ----- DELETE -----

}