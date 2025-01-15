package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.DisposalGuidelineDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.services.DisposalGuidelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wastemanagementapi")
@Tag(name = "Disposal Guidelines Management",
        description = "APIs for managing disposal guidelines for different waste categories")
public class DisposalGuidelineController {

    private final DisposalGuidelineService guidelineService;

    public DisposalGuidelineController(DisposalGuidelineService guidelineService) {
        this.guidelineService = guidelineService;
    }

    @Operation(summary = "Create a new disposal guideline",
            description = "Creates a new disposal guideline for a specific waste category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guideline created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
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

    @Operation(summary = "Get a disposal guideline by ID",
            description = "Returns a single disposal guideline based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guideline found"),
            @ApiResponse(responseCode = "404", description = "Guideline not found")
    })
    @GetMapping("/guidelines/{id}")
    public ResponseEntity<DisposalGuidelineDTO> getGuidelineById(
            @Parameter(description = "ID of the guideline to retrieve")
            @PathVariable Long id) {
        return guidelineService.getGuidelineById(id)
                .map(guideline -> new ResponseEntity<>(
                        DisposalGuidelineDTO.fromEntity(guideline),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all disposal guidelines for a category",
            description = "Returns all disposal guidelines associated with a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guidelines retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/categories/{categoryId}/guidelines")
    public ResponseEntity<List<DisposalGuidelineDTO>> getGuidelinesByCategory(
            @Parameter(description = "ID of the category")
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

    @Operation(summary = "Get all disposal guidelines",
            description = "Returns a list of all disposal guidelines across all categories")
    @ApiResponse(responseCode = "200", description = "List of guidelines retrieved successfully")
    @GetMapping("/guidelines")
    public ResponseEntity<List<DisposalGuidelineDTO>> getAllGuidelines() {
        List<DisposalGuidelineDTO> guidelineDTOs = guidelineService.getAllGuidelines()
                .stream()
                .map(DisposalGuidelineDTO::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(guidelineDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Update a disposal guideline",
            description = "Updates an existing disposal guideline with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guideline updated successfully"),
            @ApiResponse(responseCode = "404", description = "Guideline not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/guidelines/{id}")
    public ResponseEntity<DisposalGuidelineDTO> updateGuideline(
            @Parameter(description = "ID of the guideline to update")
            @PathVariable Long id,
            @Parameter(description = "Updated guideline details")
            @RequestBody DisposalGuidelineDTO guidelineDTO) {
        try {
            DisposalGuideline existingGuideline = guidelineService.getGuidelineById(id)
                    .orElseThrow(() -> new IllegalStateException("Guideline not found"));

            DisposalGuideline guidelineToUpdate = new DisposalGuideline(
                    id,
                    guidelineDTO.getTitle(),
                    guidelineDTO.getInstructions(),
                    existingGuideline.getCategory()
            );

            DisposalGuideline updated = guidelineService.updateGuideline(id, guidelineToUpdate);
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

    @Operation(summary = "Delete a disposal guideline",
            description = "Deletes a disposal guideline based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Guideline deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Guideline not found")
    })
    @DeleteMapping("/guidelines/{id}")
    public ResponseEntity<Void> deleteGuideline(
            @Parameter(description = "ID of the guideline to delete")
            @PathVariable Long id) {
        try {
            guidelineService.deleteGuideline(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}