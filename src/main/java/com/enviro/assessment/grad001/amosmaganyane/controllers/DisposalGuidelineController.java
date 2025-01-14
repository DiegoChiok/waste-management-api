package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.DisposalGuidelineDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.services.DisposalGuidelineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wastemanagementapi")
public class DisposalGuidelineController {

    private final DisposalGuidelineService guidelineService;

    public DisposalGuidelineController(DisposalGuidelineService guidelineService) {
        this.guidelineService = guidelineService;
    }

    @PostMapping("/categories/{categoryId}/guidelines")
    public ResponseEntity<DisposalGuidelineDTO> createGuideline(
            @PathVariable Long categoryId,
            @RequestBody DisposalGuidelineDTO guidelineDTO) {
        try {
            DisposalGuideline guideline = new DisposalGuideline(
                    null,
                    guidelineDTO.getTitle(),
                    guidelineDTO.getInstructions(),
                    null
            );

            DisposalGuideline created = guidelineService.createGuideline(categoryId, guideline);
            return new ResponseEntity<>(
                    DisposalGuidelineDTO.fromEntity(created),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/guidelines/{id}")
    public ResponseEntity<DisposalGuidelineDTO> getGuidelineById(@PathVariable Long id) {
        return guidelineService.getGuidelineById(id)
                .map(guideline -> new ResponseEntity<>(
                        DisposalGuidelineDTO.fromEntity(guideline),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/categories/{categoryId}/guidelines")
    public ResponseEntity<List<DisposalGuidelineDTO>> getGuidelinesByCategory(
            @PathVariable Long categoryId) {
        try {
            List<DisposalGuidelineDTO> guidelineDTOs = guidelineService
                    .getGuidelinesByCategory(categoryId)
                    .stream()
                    .map(DisposalGuidelineDTO::fromEntity)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(guidelineDTOs, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/guidelines")
    public ResponseEntity<List<DisposalGuidelineDTO>> getAllGuidelines() {
        List<DisposalGuidelineDTO> guidelineDTOs = guidelineService.getAllGuidelines()
                .stream()
                .map(DisposalGuidelineDTO::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(guidelineDTOs, HttpStatus.OK);
    }

    @PutMapping("/guidelines/{id}")
    public ResponseEntity<DisposalGuidelineDTO> updateGuideline(
            @PathVariable Long id,
            @RequestBody DisposalGuidelineDTO guidelineDTO) {
        try {
            DisposalGuideline guideline = new DisposalGuideline(
                    id,
                    guidelineDTO.getTitle(),
                    guidelineDTO.getInstructions(),
                    null
            );

            DisposalGuideline updated = guidelineService.updateGuideline(id, guideline);
            return new ResponseEntity<>(
                    DisposalGuidelineDTO.fromEntity(updated),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/guidelines/{id}")
    public ResponseEntity<Void> deleteGuideline(@PathVariable Long id) {
        try {
            guidelineService.deleteGuideline(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}