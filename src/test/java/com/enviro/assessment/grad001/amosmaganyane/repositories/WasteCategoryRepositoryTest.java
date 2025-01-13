package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WasteCategoryRepositoryTest {

    @Autowired
    private WasteCategoryRepository repository;

    @Test
    void shouldSaveAndRetrieveWasteCategory() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        WasteCategory saved = repository.save(category);

        assertNotNull(saved.getId());
        assertTrue(repository.findById(saved.getId()).isPresent());
        assertEquals("Recyclable", saved.getName());
    }

    @Test
    void shouldDeleteWasteCategory() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        WasteCategory saved = repository.save(category);

        repository.deleteById(saved.getId());

        assertFalse(repository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateWasteCategory() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        WasteCategory saved = repository.save(category);

        WasteCategory updated = new WasteCategory(saved.getId(), "Updated Name",
                "Updated description");
        repository.save(updated);

        WasteCategory found = repository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Updated Name", found.getName());
        assertEquals("Updated description", found.getDescription());
    }
}