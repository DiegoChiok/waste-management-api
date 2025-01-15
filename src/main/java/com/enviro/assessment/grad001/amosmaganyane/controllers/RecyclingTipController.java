package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.RecyclingTipDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.services.RecyclingTipService;
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
@Tag(name = "Recycling Tips Management",
        description = "APIs for managing recycling tips associated with waste categories")
public class RecyclingTipController {

    private final RecyclingTipService tipService;

    public RecyclingTipController(RecyclingTipService tipService) {
        this.tipService = tipService;
    }

    @PostMapping("/categories/{categoryId}/tips")
    @Operation(summary = "Create a new recycling tip",
            description = "Creates a new recycling tip for a specific waste category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tip created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<RecyclingTipDTO> createTip(
            @Parameter(description = "ID of the category to add the tip to")
            @PathVariable Long categoryId,
            @Parameter(description = "Recycling tip details")
            @RequestBody RecyclingTipDTO tipDTO) {
        try {
            RecyclingTip tip = new RecyclingTip(
                    null,
                    tipDTO.getTitle(),
                    tipDTO.getContent(),
                    null
            );

            RecyclingTip created = tipService.createTip(categoryId, tip);
            return new ResponseEntity<>(
                    RecyclingTipDTO.fromEntity(created),
                    HttpStatus.CREATED
            );
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a recycling tip by ID",
            description = "Returns a single recycling tip based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tip found"),
            @ApiResponse(responseCode = "404", description = "Tip not found")
    })
    @GetMapping("/tips/{id}")
    public ResponseEntity<RecyclingTipDTO> getTipById(
            @Parameter(description = "ID of the tip to retrieve")
            @PathVariable Long id) {
        return tipService.getTipById(id)
                .map(tip -> new ResponseEntity<>(
                        RecyclingTipDTO.fromEntity(tip),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all recycling tips for a category",
            description = "Returns all recycling tips associated with a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tips retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/categories/{categoryId}/tips")
    public ResponseEntity<List<RecyclingTipDTO>> getTipsByCategory(
            @Parameter(description = "ID of the category")
            @PathVariable Long categoryId) {
        try {
            List<RecyclingTipDTO> tipDTOs = tipService.getTipsByCategory(categoryId)
                    .stream()
                    .map(RecyclingTipDTO::fromEntity)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(tipDTOs, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all recycling tips",
            description = "Returns a list of all recycling tips across all categories")
    @ApiResponse(responseCode = "200", description = "List of tips retrieved successfully")
    @GetMapping("/tips")
    public ResponseEntity<List<RecyclingTipDTO>> getAllTips() {
        List<RecyclingTipDTO> tipDTOs = tipService.getAllTips()
                .stream()
                .map(RecyclingTipDTO::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tipDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Update a recycling tip",
            description = "Updates an existing recycling tip with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tip updated successfully"),
            @ApiResponse(responseCode = "404", description = "Tip not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/tips/{id}")
    public ResponseEntity<RecyclingTipDTO> updateTip(
            @Parameter(description = "ID of the tip to update")
            @PathVariable Long id,
            @Parameter(description = "Updated tip details")
            @RequestBody RecyclingTipDTO tipDTO) {
        try {
            RecyclingTip tip = new RecyclingTip(
                    id,
                    tipDTO.getTitle(),
                    tipDTO.getContent(),
                    null
            );

            RecyclingTip updated = tipService.updateTip(id, tip);
            return new ResponseEntity<>(
                    RecyclingTipDTO.fromEntity(updated),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a recycling tip",
            description = "Deletes a recycling tip based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tip deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tip not found")
    })
    @DeleteMapping("/tips/{id}")
    public ResponseEntity<Void> deleteTip(
            @Parameter(description = "ID of the tip to delete")
            @PathVariable Long id) {
        try {
            tipService.deleteTip(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get tips count for a category",
            description = "Returns the number of recycling tips associated with a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/categories/{categoryId}/tips/count")
    public ResponseEntity<Integer> getTipsCount(
            @Parameter(description = "ID of the category")
            @PathVariable Long categoryId)
     {
        try {
            int count = tipService.countTipsInCategory(categoryId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}