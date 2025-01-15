package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RecyclingTipRepositoryTest {

    @Autowired
    private RecyclingTipRepository repository;

    @Autowired
    private WasteCategoryRepository categoryRepository;

    /**
     * Tests saving and retrieving a recycling tip with its category relationship.
     */
    @Test
    void shouldSaveAndRetrieveRecyclingTip() {
        WasteCategory category = categoryRepository.save(
                new WasteCategory(null, "Recyclable", "Description")
        );

        RecyclingTip tip = new RecyclingTip(null, "Paper Recycling",
                "How to recycle paper", category);
        RecyclingTip saved = repository.save(tip);

        assertNotNull(saved.getId());
        assertTrue(repository.findById(saved.getId()).isPresent());
        assertEquals("Paper Recycling", saved.getTitle());
    }

    /**
     * Tests successful deletion of a recycling tip from the database.
     */
    @Test
    void shouldDeleteRecyclingTip() {
        WasteCategory category = categoryRepository.save(
                new WasteCategory(null, "Recyclable", "Description")
        );

        RecyclingTip tip = repository.save(
                new RecyclingTip(null, "Paper Recycling", "Content", category)
        );

        repository.deleteById(tip.getId());

        assertFalse(repository.findById(tip.getId()).isPresent());
    }
}