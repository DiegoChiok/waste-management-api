package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.repositories.DisposalGuidelineRepository;
import com.enviro.assessment.grad001.amosmaganyane.repositories.WasteCategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the DisposalGuidelineService interface.
 * Handles business logic for DisposalGuideline entities, including validation
 * and category association.
 */
@Service
public class DisposalGuidelineServiceImpl implements DisposalGuidelineService {

    private final DisposalGuidelineRepository guidelineRepository;
    private final WasteCategoryRepository categoryRepository;

    public DisposalGuidelineServiceImpl(DisposalGuidelineRepository guidelineRepository,
                                        WasteCategoryRepository categoryRepository) {
        this.guidelineRepository = guidelineRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     * Associates the guideline with a WasteCategory and validates instructions before saving.
     */
    @Override
    public DisposalGuideline createGuideline(Long categoryId, DisposalGuideline guideline) {
        WasteCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (!isValidGuidelineInstructions(guideline.getInstructions())) {
            throw new IllegalArgumentException("Invalid guideline instructions");
        }
        guideline.setCategory(category);
        return guidelineRepository.save(guideline);
    }


    /**
     * {@inheritDoc}
     * Fetches a guideline by its ID using the repository's findById method.
     */
    @Override
    public Optional<DisposalGuideline> getGuidelineById(Long id) {
        return guidelineRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     * Retrieves all guidelines using the repository's findAll method.
     */
    @Override
    public List<DisposalGuideline> getAllGuidelines() {
        return guidelineRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * Updates a guideline after checking its existence and validating instructions.
     */
    @Override
    public DisposalGuideline updateGuideline(Long id, DisposalGuideline guideline) {
        return guidelineRepository.findById(id)
                .map(existingGuideline -> {

                    existingGuideline.setTitle(guideline.getTitle());
                    existingGuideline.setInstructions(guideline.getInstructions());

                    return guidelineRepository.save(existingGuideline);
                })
                .orElseThrow(() -> new IllegalStateException("Guideline not found"));
    }

    /**
     * {@inheritDoc}
     * Deletes a guideline by its ID using the repository's deleteById method.
     */
    @Override
    public void deleteGuideline(Long id) {
        if (!guidelineRepository.existsById(id)) {
            throw new IllegalStateException("Disposal guideline not found");
        }
        guidelineRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * Validates guideline instructions to ensure they meet specified criteria.
     */
    @Override
    public boolean isValidGuidelineInstructions(String instructions) {
        return instructions != null
                && !instructions.trim().isEmpty()
                && instructions.length() >= 20
                && instructions.length() <= 1000;
    }

    @Override
    public List<DisposalGuideline> getGuidelinesByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(WasteCategory::getGuidelines)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public List<DisposalGuideline> searchGuidelines(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllGuidelines();
        }
        return guidelineRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public int countGuidelinesInCategory(Long categoryId) {
        return getGuidelinesByCategory(categoryId).size();
    }
}