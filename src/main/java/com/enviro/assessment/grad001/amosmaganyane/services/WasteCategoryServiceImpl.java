package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.WasteCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the WasteCategoryService interface.
 * Handles business logic for WasteCategory entities.
 */
@Service
public class WasteCategoryServiceImpl implements WasteCategoryService {
    public final WasteCategoryRepository repository;

    public WasteCategoryServiceImpl(WasteCategoryRepository repository){
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     * Saves a new category to the database.
     */
    @Override
    public WasteCategory createCategory(WasteCategory category) {

        if (!isValidCategoryName(category.getName())) {
            throw new IllegalArgumentException("Invalid category name format");
        }

        if (!isCategoryNameUnique(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        return repository.save(category);
    }
    /**
     * {@inheritDoc}
     * Fetches a category by ID using repository's findById method.
     */
    @Override
    public Optional<WasteCategory> getCategoryById(Long id) {
        return repository.findById(id);
    }

    /**
     * {@inheritDoc}
     * Retrieves all categories using repository's findAll method.
     */
    @Override
    public List<WasteCategory> getAllCategories() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     * Updates an existing waste category by ID with new details.
     */
    @Override
    public WasteCategory updateCategory(Long id, WasteCategory category) {
        return repository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    return repository.save(existingCategory);
                })
                .orElseThrow(() -> new IllegalStateException("Category not found"));
    }

    /**
     * {@inheritDoc}
     * Deletes a category by ID using repository's deleteById method.
     */
    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * Checks if a category name is unique by verifying it doesn't already exist in the database.
     *
     * @param name the name of the category to check
     * @return true if the category name is unique, false otherwise
     */
    @Override
    public boolean isCategoryNameUnique(String name) {
        return !repository.existsByNameIgnoreCase(name);
    }

    /**
     * {@inheritDoc}
     * Searches for categories whose names contain the given keyword (case-insensitive).
     * If the keyword is null or empty, retrieves all categories.
     *
     * @param keyword the keyword to search for
     * @return a list of categories matching the keyword or all categories if the keyword is empty
     */
    @Override
    public List<WasteCategory> searchCategories(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()){
            return getAllCategories();
        }
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * {@inheritDoc}
     * Deletes a category by ID using the repository's deleteById method.
     */
    @Override
    public boolean canDeleteCategory(Long categoryId) {
        return countGuidelinesInCategory(categoryId) == 0
                && countRecyclingTipsInCategory(categoryId) == 0;
    }

    /**
     * {@inheritDoc}
     * Validates a category name based on a few rules:
     * - Name must not be null or empty.
     * - Name length must be between 3 and 50 characters.
     *
     * @param name the category name to validate
     * @return true if the name is valid, false otherwise
     */
    @Override
    public boolean isValidCategoryName(String name) {
        return name != null
                && !name.trim().isEmpty()
                && name.length() >= 3
                && name.length() <= 50;
    }

    /**
     * {@inheritDoc}
     * Retrieves all disposal guidelines associated with a specific category ID.
     */
    @Override
    public List<DisposalGuideline> getGuidelinesForCategory(Long categoryId) {
        return repository.findById(categoryId)
                .map(WasteCategory::getGuidelines)
                .orElse(List.of());
    }

    /**
     * {@inheritDoc}
     * Retrieves all recycling tips associated with a specific category ID.
     */
    @Override
    public List<RecyclingTip> getRecyclingTipsForCategory(Long categoryId) {
        return repository.findById(categoryId)
                .map(WasteCategory::getRecyclingTips)
                .orElse(List.of());
    }


    /**
     * {@inheritDoc}
     * Counts the number of disposal guidelines associated with a specific category ID.
     */
    @Override
    public int countGuidelinesInCategory(Long categoryId) {
        return getGuidelinesForCategory(categoryId).size();
    }

    /**
     * {@inheritDoc}
     * Counts the number of recycling tips associated with a specific category ID.
     */
    @Override
    public int countRecyclingTipsInCategory(Long categoryId) {
        return getRecyclingTipsForCategory(categoryId).size();
    }

    /**
     * {@inheritDoc}
     * Retrieves a limited list of categories with the most associated guidelines.
     */
    @Override
    public List<WasteCategory> getCategoriesWithMostGuidelines(int limit) {
        return repository.findTopCategoriesByGuidelineCount(limit);
    }

}
