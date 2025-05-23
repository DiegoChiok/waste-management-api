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

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        DisposalGuideline existingGuideline = new DisposalGuideline(guidelineId,
                "Old Title", "Old instructions", testCategory);
        DisposalGuideline updateRequest = new DisposalGuideline(guidelineId,
                "Updated Title",
                "Updated detailed instructions that meet the minimum length requirement",
                null);
        DisposalGuideline expectedResult = new DisposalGuideline(guidelineId,
                "Updated Title",
                "Updated detailed instructions that meet the minimum length requirement",
                testCategory);

        when(guidelineRepository.findById(guidelineId))
                .thenReturn(Optional.of(existingGuideline));
        when(guidelineRepository.save(any(DisposalGuideline.class)))
                .thenReturn(expectedResult);

        DisposalGuideline result = service.updateGuideline(guidelineId, updateRequest);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated detailed instructions that meet the minimum length requirement",
                result.getInstructions());
        assertEquals(testCategory, result.getCategory());
        verify(guidelineRepository).findById(guidelineId);
        verify(guidelineRepository).save(any(DisposalGuideline.class));
    }

    /**
     * Tests that a guideline update fails when the guideline doesn't exist.
     * Verifies that the service throws IllegalStateException and never calls save.
     */
    @Test
    void testUpdateGuidelineNotFound() {
        Long nonExistentId = 999L;
        DisposalGuideline updateRequest = new DisposalGuideline(nonExistentId,
                "Title", "Instructions", null);

        when(guidelineRepository.findById(nonExistentId))
                .thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> service.updateGuideline(nonExistentId, updateRequest));
        verify(guidelineRepository).findById(nonExistentId);
        verify(guidelineRepository, never()).save(any());
    }

    /**
     * Tests the search functionality with a matching keyword.
     * Verifies that the search returns guidelines with titles containing the keyword.
     */
    @Test
    void testSearchGuidelinesSuccessfully() {
        String keyword = "battery";
        List<DisposalGuideline> expectedGuidelines = List.of(
                new DisposalGuideline(1L, "Battery Disposal",
                        "Detailed instructions for battery disposal", testCategory)
        );
        when(guidelineRepository.findByTitleContainingIgnoreCase(keyword))
                .thenReturn(expectedGuidelines);

        List<DisposalGuideline> results = service.searchGuidelines(keyword);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertTrue(results.get(0).getTitle().toLowerCase().contains(keyword));
    }

    /**
     * Tests the validation of guideline instructions.
     * Verifies that instructions meet length requirements and aren't null or empty.
     */
    @Test
    void testValidateGuidelineInstructions() {
        String validInstructions = "These are proper disposal instructions that provide " +
                "detailed steps for waste handling and safety precautions.";
        assertTrue(service.isValidGuidelineInstructions(validInstructions));

        assertFalse(service.isValidGuidelineInstructions("Too short"));
        assertFalse(service.isValidGuidelineInstructions(""));
        assertFalse(service.isValidGuidelineInstructions(null));
    }

    /**
     * Tests retrieval of guidelines for a specific category.
     * Verifies that all guidelines associated with the category are returned.
     */
    @Test
    void testGetGuidelinesByCategory() {
        Long categoryId = 1L;
        WasteCategory mockCategory = mock(WasteCategory.class);
        List<DisposalGuideline> expectedGuidelines = List.of(
                new DisposalGuideline(1L, "Guideline 1",
                        "Detailed instructions for guideline 1", mockCategory),
                new DisposalGuideline(2L, "Guideline 2",
                        "Detailed instructions for guideline 2", mockCategory)
        );

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(mockCategory));
        when(mockCategory.getGuidelines()).thenReturn(expectedGuidelines);

        List<DisposalGuideline> results = service.getGuidelinesByCategory(categoryId);

        assertEquals(2, results.size());
        verify(categoryRepository).findById(categoryId);
    }

    /**
     * Tests search functionality when no matches are found.
     * Verifies that an empty list is returned for non-existent keywords.
     */
    @Test
    void testHandleEmptySearchResults() {
        String nonExistentKeyword = "nonexistent";
        when(guidelineRepository.findByTitleContainingIgnoreCase(nonExistentKeyword))
                .thenReturn(List.of());

        List<DisposalGuideline> results = service.searchGuidelines(nonExistentKeyword);

        assertTrue(results.isEmpty());
    }

    /**
     * Tests the counting of guidelines in a category.
     * Verifies that the correct count of guidelines is returned for a category.
     */
    @Test
    void testCountGuidelinesInCategory() {
        Long categoryId = 1L;
        WasteCategory mockCategory = mock(WasteCategory.class);
        List<DisposalGuideline> guidelines = List.of(
                new DisposalGuideline(1L, "Guideline 1",
                        "Instructions 1", mockCategory),
                new DisposalGuideline(2L, "Guideline 2",
                        "Instructions 2", mockCategory)
        );

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(mockCategory));
        when(mockCategory.getGuidelines()).thenReturn(guidelines);

        int count = service.countGuidelinesInCategory(categoryId);

        assertEquals(2, count);
    }

    /**
     * Tests search functionality with a specific keyword.
     * Verifies that search is case-insensitive and returns matching guidelines.
     */
    @Test
    void testSearchGuidelinesWithKeyword() {
        String keyword = "battery";
        List<DisposalGuideline> expectedGuidelines = List.of(
                new DisposalGuideline(1L, "Battery Disposal",
                        "Instructions", testCategory)
        );
        when(guidelineRepository.findByTitleContainingIgnoreCase(keyword))
                .thenReturn(expectedGuidelines);

        List<DisposalGuideline> results = service.searchGuidelines(keyword);

        assertEquals(1, results.size());
        assertEquals("Battery Disposal", results.get(0).getTitle());
        verify(guidelineRepository).findByTitleContainingIgnoreCase(keyword);
    }

    /**
     * Tests search functionality with an empty keyword.
     * Verifies that all guidelines are returned when search term is empty.
     */
    @Test
    void testSearchGuidelinesWithEmptyKeyword() {
        List<DisposalGuideline> allGuidelines = List.of(
                new DisposalGuideline(1L, "Guideline 1",
                        "Instructions 1", testCategory),
                new DisposalGuideline(2L, "Guideline 2",
                        "Instructions 2", testCategory)
        );
        when(guidelineRepository.findAll()).thenReturn(allGuidelines);

        List<DisposalGuideline> results = service.searchGuidelines("");

        assertEquals(2, results.size());
        verify(guidelineRepository).findAll();
        verify(guidelineRepository, never()).findByTitleContainingIgnoreCase(any());
    }
}
