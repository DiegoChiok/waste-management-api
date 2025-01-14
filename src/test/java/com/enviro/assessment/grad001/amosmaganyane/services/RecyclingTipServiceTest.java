package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.RecyclingTipRepository;
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
 * Unit tests for RecyclingTipService.
 * Verifies service logic using mocked repositories.
 */
@ExtendWith(MockitoExtension.class)
class RecyclingTipServiceTest {

    @Mock
    private RecyclingTipRepository tipRepository;

    @Mock
    private WasteCategoryRepository categoryRepository;

    private RecyclingTipService service;
    private WasteCategory testCategory;

    /**
     * Initializes the service with mocked repositories before each test.
     */
    @BeforeEach
    void initializeRepositories() {
        service = new RecyclingTipServiceImpl(tipRepository, categoryRepository);
        testCategory = new WasteCategory(1L, "Recyclable", "Description");
    }


    /**
     * Verifies that a tip is successfully created and saved.
     */
    @Test
    void testCreateTipSuccessfully() {
        Long categoryId = 1L;
        RecyclingTip tip = new RecyclingTip(null, "Paper Recycling",
                "How to recycle paper properly", testCategory);

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(testCategory));
        when(tipRepository.save(any(RecyclingTip.class)))
                .thenReturn(
                new RecyclingTip(1L, tip.getTitle(), tip.getContent(), testCategory));

        RecyclingTip created = service.createTip(categoryId, tip);

        assertNotNull(created.getId());
        assertEquals("Paper Recycling", created.getTitle());
        verify(tipRepository).save(any(RecyclingTip.class));
    }

    /**
     * Verifies exception is thrown for invalid tip content.
     */
    @Test
    void testThrowExceptionWhenCreatingTipWithInvalidContent() {
        Long categoryId = 1L;
        RecyclingTip tip = new RecyclingTip(null, "Title", "Too short", testCategory);

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(testCategory));

        assertThrows(IllegalArgumentException.class,
                () -> service.createTip(categoryId, tip));
    }


    /**
     * Verifies exception is thrown when the category does not exist.
     */
    @Test
    void testThrowExceptionWhenCategoryNotFound() {
        Long nonExistentCategoryId = 999L;
        RecyclingTip tip = new RecyclingTip(null, "Title",
                "Valid content for recycling", testCategory);

        when(categoryRepository.findById(nonExistentCategoryId))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.createTip(nonExistentCategoryId, tip));
    }

    @Test
    void testUpdateTipSuccessfully() {
        Long tipId = 1L;
        RecyclingTip updatedTip = new RecyclingTip(tipId, "Updated Title",
                "Updated content that is valid", testCategory);

        when(tipRepository.existsById(tipId)).thenReturn(true);
        when(tipRepository.save(any(RecyclingTip.class))).thenReturn(updatedTip);

        RecyclingTip result = service.updateTip(tipId, updatedTip);

        assertEquals("Updated Title", result.getTitle());
        verify(tipRepository).save(any(RecyclingTip.class));
    }
}
