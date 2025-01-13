package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.WasteCategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the WasteCategoryService interface.
 * Handles business logic for WasteCategory entities.
 */
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

    @Override
    public WasteCategory updateCategory(Long id, WasteCategory category) {
        // TODO: later figure out if more logic is needed for updating
        return null;
    }


    /**
     * {@inheritDoc}
     * Deletes a category by ID using repository's deleteById method.
     */
    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}
