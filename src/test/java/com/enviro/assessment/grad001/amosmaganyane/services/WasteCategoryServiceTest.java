package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for WasteCategoryService.
 * Verifies the service logic  using a mocked repositories.
 */
@ExtendWith(MockitoExtension.class)
class WasteCategoryServiceTest {
    private WasteCategoryService service;

    @Mock
    private WasteCategoryRepository wasteCategoryRepository;

    /**
     * Initializes the service with a mocked repository before each test.
     */
    @BeforeEach
    void initializeService(){
        service = new WasteCategoryServiceImpl(wasteCategoryRepository);
    }

    /**
     * Verifies that a category is successfully created and saved.
     */
    @Test
    void testCreateCategory(){
        WasteCategory category = new WasteCategory(null,"Recyclable","Description");
        when(wasteCategoryRepository.save(any(WasteCategory.class)))
                .thenReturn(new WasteCategory(1L, "Recyclable", "Description"));

        WasteCategory created = service.createCategory(category);
        assertNotNull(created.getId());
        assertEquals("Recyclable", created.getName());
        verify(wasteCategoryRepository).save(any(WasteCategory.class));
    }


    /**
     * Verifies that a category is retrieved by its ID when it exists.
     */
    @Test
    void testGetCategoryById(){
        Long id = 1L;
        WasteCategory category = new WasteCategory(id, "Recyclable", "Description");
        when(wasteCategoryRepository.findById(id))
                .thenReturn(Optional.of(category));

        Optional<WasteCategory> found = service.getCategoryById(id);

        assertTrue(found.isPresent());
        assertEquals("Recyclable", found.get().getName());
    }

    /**
     * Verifies that an empty Optional is returned when the category is not found.
     */
    @Test
    void shouldReturnEmptyWhenCategoryNotFound() {
        Long id = 1L;
        when(wasteCategoryRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<WasteCategory> found = service.getCategoryById(id);

        assertTrue(found.isEmpty());
    }

    /**
     * Verifies that duplicate category names are detected and prevented.
     */
    @Test
    void shouldPreventDuplicateCategoryNames() {
        String existingName = "Recyclable";
        when(wasteCategoryRepository.existsByNameIgnoreCase(existingName)).thenReturn(true);

        boolean isUnique = service.isCategoryNameUnique(existingName);

        assertFalse(isUnique);
    }


    /**
     * Verifies various formats for category names.
     */
    @Test
    void shouldValidateCategoryNameFormat() {
        assertFalse(service.isValidCategoryName("A"));
        assertFalse(service.isValidCategoryName("   "));
        assertTrue(service.isValidCategoryName("Recyclable Waste"));
        assertFalse(service.isValidCategoryName("Too Long Category Name " +
                "That Exceeds The Maximum Allowed Length"));
    }

    /**
     * Verifies cases where a search returns no results are well handled.
     */
    @Test
    void shouldHandleEmptySearchResults() {
        String nonExistentKeyword = "NonExistent";
        when(wasteCategoryRepository.findByNameContainingIgnoreCase(nonExistentKeyword))
                .thenReturn(List.of());

        List<WasteCategory> results = service.searchCategories(nonExistentKeyword);

        assertTrue(results.isEmpty());
    }

    /**
     * Verifies that all categories are returned when the search keyword is null.
     */
    @Test
    void shouldHandleNullSearchKeyword() {
        List<WasteCategory> allCategories = List.of(
                new WasteCategory(1L, "Recyclable", "Description"),
                new WasteCategory(2L, "Organic", "Description")
        );

        when(wasteCategoryRepository.findAll()).thenReturn(allCategories);

        List<WasteCategory> results = service.searchCategories(null);

        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
    }

    @Test
    void shouldCheckIfCategoryCanBeDeleted() {
        Long id = 1L;
        WasteCategory category = new WasteCategory(id, "Recyclable", "Description");
        when(wasteCategoryRepository.findById(id)).thenReturn(Optional.of(category));

        boolean canDelete = service.canDeleteCategory(id);

        assertTrue(canDelete);
    }


    @Test
    void testCountGuidelinesInCategory() {
        Long categoryId = 1L;
        WasteCategory category = new WasteCategory(categoryId, "Recyclable", "Description");
        when(wasteCategoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        int count = service.countGuidelinesInCategory(categoryId);

        assertEquals(0, count);
    }

    @Test
    void testGetCategoriesWithMostGuidelines() {
        int limit = 2;
        List<WasteCategory> topCategories = List.of(
                new WasteCategory(1L, "Recyclable", "Most guidelines"),
                new WasteCategory(2L, "Organic", "Second most guidelines")
        );
        when(wasteCategoryRepository.findTopCategoriesByGuidelineCount(limit))
                .thenReturn(topCategories);

        List<WasteCategory> result = service.getCategoriesWithMostGuidelines(limit);

        assertEquals(2, result.size());
        verify(wasteCategoryRepository).findTopCategoriesByGuidelineCount(limit);
    }
}
