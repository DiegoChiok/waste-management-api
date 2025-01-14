package com.enviro.assessment.grad001.amosmaganyane.services;


import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.amosmaganyane.repositories.WasteCategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for DisposalGuidelineService.
 * Verifies the service logic using mocked repositories.
 */
@ExtendWith(MockitoExtension.class)
class DisposalGuidelineServiceTest {

    @Mock
    private DisposalGuidelineRepository guidelineRepository;

    @Mock
    private WasteCategoryRepository categoryRepository;

    private DisposalGuidelineService service;
    private WasteCategory testCategory;

    /**
     * Initializes the service and mock repositories before each test.
     */
    @BeforeEach
    void initializeRepositories() {
        service = new DisposalGuidelineServiceImpl(guidelineRepository, categoryRepository);
        testCategory = new WasteCategory(1L, "Hazardous Waste", "Description");
    }

    /**
     * Verifies successful creation of a disposal guideline.
     */
    @Test
    void testCreateGuidelineSuccessfully() {
        Long categoryId = 1L;
        DisposalGuideline guideline = new DisposalGuideline(null, "Battery Disposal",
                "Detailed instructions for proper battery disposal. Must be taken to designated collection points.",
                testCategory);

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(testCategory));
        when(guidelineRepository.save(any(DisposalGuideline.class)))
                .thenReturn(new DisposalGuideline(1L, guideline.getTitle(),
                        guideline.getInstructions(), testCategory));

        DisposalGuideline created = service.createGuideline(categoryId, guideline);

        assertNotNull(created.getId());
        assertEquals("Battery Disposal", created.getTitle());
        verify(guidelineRepository).save(any(DisposalGuideline.class));
    }

    /**
     * Verifies exception is thrown when creating a guideline with invalid instructions.
     */
    @Test
    void testThrowExceptionWhenCreatingGuidelineWithInvalidInstructions() {
        Long categoryId = 1L;
        DisposalGuideline guideline = new DisposalGuideline(null, "Title",
                "Too short", testCategory);
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(testCategory));

        assertThrows(IllegalArgumentException.class,
                () -> service.createGuideline(categoryId, guideline));
    }

    /**
     * Verifies exception is thrown when the associated category is not found.
     */
    @Test
    void testThrowExceptionWhenCategoryNotFound() {
        Long nonExistentCategoryId = 999L;
        DisposalGuideline guideline = new DisposalGuideline(null, "Title",
                "These are valid disposal instructions that meet the minimum length requirement.",
                testCategory);

        when(categoryRepository.findById(nonExistentCategoryId))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.createGuideline(nonExistentCategoryId, guideline));
    }

    /**
     * Verifies if an existing guideline can be updated successfully.
     */
    @Test
    void testUpdateGuidelineSuccessfully() {
        Long guidelineId = 1L;
        DisposalGuideline updatedGuideline = new DisposalGuideline(guidelineId,
                "Updated Title",
                "Updated detailed instructions that meet the minimum length requirement",
                testCategory);

        when(guidelineRepository.existsById(guidelineId))
                .thenReturn(true);
        when(guidelineRepository.save(any(DisposalGuideline.class)))
                .thenReturn(updatedGuideline);

        DisposalGuideline result = service.updateGuideline(guidelineId, updatedGuideline);

        assertEquals("Updated Title", result.getTitle());
        verify(guidelineRepository).save(any(DisposalGuideline.class));
    }

}
