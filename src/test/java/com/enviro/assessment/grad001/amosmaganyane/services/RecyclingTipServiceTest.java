package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.RecyclingTipRepository;
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

    /**
     * Verifies if an existing tip can be updated successfully.
     */
    @Test
    void testUpdateTipSuccessfully() {
        // Given
        Long tipId = 1L;
        RecyclingTip existingTip = new RecyclingTip(tipId, "Old Title",
                "Old content", testCategory);
        RecyclingTip updateRequest = new RecyclingTip(tipId, "Updated Title",
                "Updated content", null);
        RecyclingTip expectedResult = new RecyclingTip(tipId, "Updated Title",
                "Updated content", testCategory);

        when(tipRepository.findById(tipId)).thenReturn(Optional.of(existingTip));
        when(tipRepository.save(any(RecyclingTip.class))).thenReturn(expectedResult);

        RecyclingTip result = service.updateTip(tipId, updateRequest);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated content", result.getContent());
        assertEquals(testCategory, result.getCategory());
        verify(tipRepository).findById(tipId);
        verify(tipRepository).save(any(RecyclingTip.class));
    }

    @Test
    void testUpdateTipNotFound() {
        Long nonExistentId = 999L;
        RecyclingTip updateRequest = new RecyclingTip(nonExistentId, "Title",
                "Content", null);

        when(tipRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> service.updateTip(nonExistentId, updateRequest));
        verify(tipRepository).findById(nonExistentId);
        verify(tipRepository, never()).save(any());
    }

    @Test
    void shouldSearchTipsSuccessfully() {
        String keyword = "paper";
        List<RecyclingTip> expectedTips = List.of(
                new RecyclingTip(1L, "Paper Recycling",
                        "Content about paper recycling", testCategory)
        );
        when(tipRepository.findByTitleContainingIgnoreCase(keyword))
                .thenReturn(expectedTips);

        List<RecyclingTip> results = service.searchTips(keyword);

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertTrue(results.get(0).getTitle().toLowerCase().contains(keyword));
    }

    @Test
    void testValidateTipContent() {
        assertTrue(service.isValidTipContent(
                "This is a valid recycling tip content with proper length"));
        assertFalse(service.isValidTipContent("Too short"));
        assertFalse(service.isValidTipContent(""));
        assertFalse(service.isValidTipContent(null));
    }

    @Test
    void testGetTipsByCategory() {
        Long categoryId = 1L;
        WasteCategory mockCategory = mock(WasteCategory.class);
        List<RecyclingTip> expectedTips = List.of(
                new RecyclingTip(1L, "Tip 1", "Content 1", mockCategory),
                new RecyclingTip(2L, "Tip 2", "Content 2", mockCategory)
        );

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(mockCategory));
        when(mockCategory.getRecyclingTips())
                .thenReturn(expectedTips);

        List<RecyclingTip> results = service.getTipsByCategory(categoryId);

        assertEquals(2, results.size());
        verify(categoryRepository).findById(categoryId);
        verify(mockCategory).getRecyclingTips();
    }

    @Test
    void testCountRecyclingTipsInCategory() {
        Long categoryId = 1L;
        WasteCategory mockCategory = mock(WasteCategory.class);
        List<RecyclingTip> tips = List.of(
                new RecyclingTip(1L, "Tip 1",
                        "Content 1", mockCategory),
                new RecyclingTip(2L, "Tip 2",
                        "Content 2", mockCategory)
        );

        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(mockCategory));
        when(mockCategory.getRecyclingTips()).thenReturn(tips);

        int count = service.countTipsInCategory(categoryId);

        assertEquals(2, count);
    }
}
