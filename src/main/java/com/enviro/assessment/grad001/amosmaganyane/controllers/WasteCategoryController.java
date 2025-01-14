package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.WasteCategoryDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.services.WasteCategoryService;
import jdk.jfr.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wastemanagementapi/categories")
public class WasteCategoryController {

    private final WasteCategoryService categoryService;

    public WasteCategoryController(WasteCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<WasteCategoryDTO> createCategory(@RequestBody WasteCategoryDTO categoryDTO) {
        WasteCategory category = new WasteCategory(
                null,
                categoryDTO.getName(),
                categoryDTO.getDescription()
        );

        WasteCategory created = categoryService.createCategory(category);
        return new ResponseEntity<>(WasteCategoryDTO.fromEntity(created)
                , HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WasteCategoryDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> new ResponseEntity<>(
                        WasteCategoryDTO.fromEntity(category),
                        HttpStatus.OK
                ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<WasteCategoryDTO>> getAllCategories() {
        List<WasteCategoryDTO> categoryDTOS = categoryService.getAllCategories()
                .stream()
                .map(WasteCategoryDTO::fromEntity)
                .toList();
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WasteCategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody WasteCategoryDTO categoryDTO) {
        try {
            WasteCategory category = new WasteCategory(
                    id,
                    categoryDTO.getName(),
                    categoryDTO.getDescription()
            );

            WasteCategory updated = categoryService.updateCategory(id, category);
            return new ResponseEntity<>(
                    WasteCategoryDTO.fromEntity(updated),
                    HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}