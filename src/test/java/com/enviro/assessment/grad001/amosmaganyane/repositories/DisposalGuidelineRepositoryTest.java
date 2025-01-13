package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DisposalGuidelineRepositoryTest {

    @Autowired
    private DisposalGuidelineRepository repository;

    @Autowired
    private WasteCategoryRepository categoryRepository;

    @Test
    void shouldSaveAndRetrieveDisposalGuideline() {
        WasteCategory category = categoryRepository.save(
                new WasteCategory(null, "Recyclable", "Description")
        );

        DisposalGuideline guideline = new DisposalGuideline(null, "Paper Disposal",
                "How to dispose paper", category);
        DisposalGuideline saved = repository.save(guideline);

        assertNotNull(saved.getId());
        assertTrue(repository.findById(saved.getId()).isPresent());
        assertEquals("Paper Disposal", saved.getTitle());
    }

    @Test
    void shouldDeleteDisposalGuideline() {
        WasteCategory category = categoryRepository.save(
                new WasteCategory(null, "Recyclable", "Description")
        );

        DisposalGuideline guideline = repository.save(
                new DisposalGuideline(null, "Paper Disposal",
                        "Instructions", category)
        );

        repository.deleteById(guideline.getId());

        assertFalse(repository.findById(guideline.getId()).isPresent());
    }
}