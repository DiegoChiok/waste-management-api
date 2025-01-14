package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
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
}
