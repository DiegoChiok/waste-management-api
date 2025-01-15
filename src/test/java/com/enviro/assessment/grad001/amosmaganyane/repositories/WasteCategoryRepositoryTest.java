package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class WasteCategoryRepositoryTest {

    @Autowired
    private WasteCategoryRepository repository;

    /**
     * Tests saving and retrieving a waste category with basic attributes.
     */
    @Test
    void testSaveAndRetrieveWasteCategory() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        WasteCategory saved = repository.save(category);

        assertNotNull(saved.getId());
        assertTrue(repository.findById(saved.getId()).isPresent());
        assertEquals("Recyclable", saved.getName());
    }

    /**
     * Tests successful deletion of a waste category from the database.
     */
    @Test
    void testDeleteWasteCategory() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        WasteCategory saved = repository.save(category);

        repository.deleteById(saved.getId());

        assertFalse(repository.findById(saved.getId()).isPresent());
    }

    /**
     * Tests updating an existing waste category's properties.
     */
    @Test
    void testUpdateWasteCategory() {
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

    /**
     * Tests case-insensitive search functionality for category names.
     */
    @Test
    void testFindByNameContainingIgnoreCase() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        repository.save(category);

        List<WasteCategory> found = repository.findByNameContainingIgnoreCase("cycl");

        assertFalse(found.isEmpty());
        assertEquals("Recyclable", found.get(0).getName());

        List<WasteCategory> notFound = repository.findByNameContainingIgnoreCase("xyz");
        assertTrue(notFound.isEmpty());
    }

    /**
     * Tests case-insensitive existence check for category names.
     */
    @Test
    void testCheckExistsByNameIgnoreCase() {
        WasteCategory category = new WasteCategory(null, "Recyclable",
                "Items that can be recycled");
        repository.save(category);

        assertTrue(repository.existsByNameIgnoreCase("RECYCLABLE"));
        assertFalse(repository.existsByNameIgnoreCase("NonExistent"));
    }
}