package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.entity.LessonResource;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.resource.LessonResourceCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.resource.LessonResourceResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.LessonResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LessonResourceService {
    private final LessonResourceRepository lessonResourceRepository;
    private final LessonService lessonService;

    public LessonResourceService(
            LessonResourceRepository lessonResourceRepository,
            LessonService lessonService) {
        this.lessonResourceRepository = lessonResourceRepository;
        this.lessonService = lessonService;
    }

    @Transactional(readOnly = true)
    public LessonResourceResponseDto findById(Long resourceId) {
        LessonResource lessonResource = lessonResourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson resource with id: " + resourceId + " not found."
                ));
        return new LessonResourceResponseDto(lessonResource);
    }

    @Transactional(readOnly = true)
    public List<LessonResourceResponseDto> findAllByLessonId(Long lessonId) {
        // Valida se a lesson existe
        lessonService.findById(lessonId);

        List<LessonResource> lessonResources = lessonResourceRepository.findByLessonId(lessonId);
        return lessonResources.stream()
                .map(LessonResourceResponseDto::new)
                .toList();
    }

    @Transactional
    public LessonResourceResponseDto save(LessonResourceCreationDto lessonResourceCreationDto) {
        // Busca a lesson através do LessonService (nunca diretamente pelo repositório)
        Lesson lesson = lessonService.findEntityById(lessonResourceCreationDto.lessonId());

        LessonResource lessonResource = new LessonResource(lessonResourceCreationDto, lesson);
        LessonResource saved = lessonResourceRepository.save(lessonResource);

        return new LessonResourceResponseDto(saved);
    }
}

