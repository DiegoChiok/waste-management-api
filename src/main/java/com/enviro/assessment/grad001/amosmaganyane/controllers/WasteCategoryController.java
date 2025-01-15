package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.WasteCategoryDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.services.WasteCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wastemanagementapi/categories")
@Tag(name = "Waste Category Management", description = "APIs for managing waste categories")
public class WasteCategoryController {

    private final WasteCategoryService categoryService;

    public WasteCategoryController(WasteCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new waste category",
            description = "Creates a new waste category with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @Valid
    public ResponseEntity<WasteCategoryDTO> createCategory(
            @Parameter(description = "Waste category to create")
            @RequestBody WasteCategoryDTO categoryDTO) {
        try {
            WasteCategory category = new WasteCategory(
                    null,
                    categoryDTO.getName(),
                    categoryDTO.getDescription()
            );

            WasteCategory created = categoryService.createCategory(category);
            return new ResponseEntity<>(WasteCategoryDTO.fromEntity(created),
                    HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get a waste category by ID",
            description = "Returns a single waste category based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WasteCategoryDTO> getCategoryById(
            @Parameter(description = "ID of the category to retrieve")
            @PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> new ResponseEntity<>(
                        WasteCategoryDTO.fromEntity(category),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all waste categories",
            description = "Returns a list of all waste categories")
    @ApiResponse(responseCode = "200", description = "List of categories retrieved successfully")
    @GetMapping
    public ResponseEntity<List<WasteCategoryDTO>> getAllCategories() {
        List<WasteCategoryDTO> categoryDTOS = categoryService.getAllCategories()
                .stream()
                .map(WasteCategoryDTO::fromEntity)
                .toList();
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Update a waste category",
            description = "Updates an existing waste category with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<WasteCategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody WasteCategoryDTO categoryDTO) {
        try {
            WasteCategory existingCategory = categoryService.getCategoryById(id)
                    .orElseThrow(() -> new IllegalStateException("Category not found"));

            WasteCategory categoryToUpdate = new WasteCategory(
                    id,
                    categoryDTO.getName(),
                    categoryDTO.getDescription()
            );

            WasteCategory updated = categoryService.updateCategory(id, categoryToUpdate);
            return new ResponseEntity<>(
                    WasteCategoryDTO.fromEntity(updated),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a waste category",
            description = "Deletes a waste category based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete") @PathVariable Long id)
     {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Search waste categories",
            description = "Search for waste categories based on a keyword")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<List<WasteCategoryDTO>> searchCategories(
            @Parameter(description = "Keyword to search for in category names")
            @RequestParam(required = false) String keyword) {

        List<WasteCategoryDTO> categoryDTOs = categoryService.searchCategories(keyword)
                .stream()
                .map(WasteCategoryDTO::fromEntity)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get guidelines count for a category",
            description = "Returns the number of disposal guidelines associated with a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}/guidelines/count")
    public ResponseEntity<Integer> getGuidelinesCount(
            @Parameter(description = "ID of the category") @PathVariable Long id) {
        try {
            int count = categoryService.countGuidelinesInCategory(id);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get tips count for a category",
            description = "Returns the number of recycling tips associated with a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}/tips/count")
    public ResponseEntity<Integer> getTipsCount(
            @Parameter(description = "ID of the category") @PathVariable Long id) {
        try {
            int count = categoryService.countRecyclingTipsInCategory(id);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}