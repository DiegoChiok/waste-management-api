package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import java.util.List;
import java.util.Optional;


/**
 * WasteCategoryService interface defines the contract for managing waste categories,
 * including CRUD operations, validation, and associated guidelines or tips management.
 */
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

    // Business operations
    // Manage categories

    /**
     * Checks if a category name is unique (case-insensitive).
     *
     * @param name the name to check
     * @return true if the name is unique, false otherwise
     */
    boolean isCategoryNameUnique(String name);

    /**
     * Searches for categories matching the given keyword.
     *
     * @param keyword the search keyword
     * @return a list of matching WasteCategory objects
     */
    List<WasteCategory> searchCategories(String keyword);

    // Validation
    boolean canDeleteCategory(Long categoryId);

    /**
     * Validates the format of a category name.
     *
     * @param name the name to validate
     * @return true if the name is valid, false otherwise
     */
    boolean isValidCategoryName(String name);

    // Guidelines and Tips Management
    /**
     * Retrieves all disposal guidelines associated with a specific waste category.
     *
     * @param categoryId the ID of the category
     * @return a list of DisposalGuideline objects for the category
     */
    List<DisposalGuideline> getGuidelinesForCategory(Long categoryId);

    /**
     * Retrieves all disposal guidelines associated with a specific waste category.
     *
     * @param categoryId the ID of the category
     * @return a list of DisposalGuideline objects for the category
     */
    List<RecyclingTip> getRecyclingTipsForCategory(Long categoryId);

    // Statistics and Reporting
    /**
     * Counts the number of disposal guidelines in a specific category.
     *
     * @param categoryId the ID of the category
     * @return the number of guidelines in the category
     */
    int countGuidelinesInCategory(Long categoryId);

    /**
     * Counts the number of recycling tips in a specific category.
     *
     * @param categoryId the ID of the category
     * @return the number of recycling tips in the category
     */
    int countRecyclingTipsInCategory(Long categoryId);

    /**
     * Retrieves a limited list of categories with the most associated guidelines.
     *
     * @param limit the maximum number of categories to retrieve
     * @return a list of WasteCategory objects with the most guidelines
     */
    List<WasteCategory> getCategoriesWithMostGuidelines(int limit);

}
