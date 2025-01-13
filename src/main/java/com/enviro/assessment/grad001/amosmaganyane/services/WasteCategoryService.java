package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import java.util.List;
import java.util.Optional;

// Waste Category Service with basic CRUD operations
public interface WasteCategoryService {

    /**
     * Creates and persists a new waste category.
     *
     * @param category the WasteCategory to create
     * @return the created WasteCategory with a generated ID
    */
    WasteCategory createCategory(WasteCategory category);

    /**
     * Retrieves a waste category by its unique ID.
     *
     * @param id the ID of the category to fetch
     * @return an Optional containing the WasteCategory if found or empty if not
     */
    Optional<WasteCategory> getCategoryById(Long id);

    /**
     * Retrieves all waste categories.
     *
     * @return a list of all WasteCategory objects
     *
     */
    List<WasteCategory> getAllCategories();

    /**
     * Updates an existing waste category.
     *
     * @param id       the ID of the category to update
     * @param category the updated WasteCategory data
     * @return the updated WasteCategory
     */
    WasteCategory updateCategory(Long id, WasteCategory category);

    /**
     * Deletes a waste category by its unique ID.
     *
     * @param id the ID of the category to delete
     */
    void deleteCategory(Long id);
}
