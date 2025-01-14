package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.RecyclingTipRepository;
import com.enviro.assessment.grad001.amosmaganyane.repositories.WasteCategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the RecyclingTipService interface.
 * Handles business logic for RecyclingTip entities, including validation and category association.
 */
@Service
public class RecyclingTipServiceImpl implements RecyclingTipService {

    private final RecyclingTipRepository tipRepository;
    private final WasteCategoryRepository categoryRepository;

    public RecyclingTipServiceImpl(RecyclingTipRepository tipRepository,
                                   WasteCategoryRepository categoryRepository) {
        this.tipRepository = tipRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     * Associates the tip with a WasteCategory and validates content before saving.
     */
    @Override
    public RecyclingTip createTip(Long categoryId, RecyclingTip tip) {
        WasteCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (!isValidTipContent(tip.getContent())) {
            throw new IllegalArgumentException("Invalid tip content");
        }

        return tipRepository.save(tip);
    }

    /**
     * {@inheritDoc}
     * Fetches a tip by its ID using the repository's findById method.
     */
    @Override
    public Optional<RecyclingTip> getTipById(Long id) {
        return tipRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     * Retrieves all tips using the repository's findAll method.
     */
    @Override
    public List<RecyclingTip> getAllTips() {
        return tipRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * Updates a tip after checking its existence and validating content.
     */
    @Override
    public RecyclingTip updateTip(Long id, RecyclingTip tip) {
        if (!tipRepository.existsById(id)) {
            throw new IllegalStateException("Recycling tip not found");
        }

        if (!isValidTipContent(tip.getContent())) {
            throw new IllegalArgumentException("Invalid tip content");
        }

        return tipRepository.save(tip);
    }


    /**
     * {@inheritDoc}
     * Deletes a tip by its ID using the repository's deleteById method.
     */
    @Override
    public void deleteTip(Long id) {
        if (!tipRepository.existsById(id)) {
            throw new IllegalStateException("Recycling tip not found");
        }
        tipRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * Validates tip content to ensure it meets specified criteria.
     */
    @Override
    public boolean isValidTipContent(String content) {
        return content != null
                && !content.trim().isEmpty()
                && content.length() >= 10
                && content.length() <= 500;
    }


    @Override
    public List<RecyclingTip> getTipsByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(WasteCategory::getRecyclingTips)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public List<RecyclingTip> searchTips(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllTips();
        }
        return tipRepository.findByTitleContainingIgnoreCase(keyword);
    }
}