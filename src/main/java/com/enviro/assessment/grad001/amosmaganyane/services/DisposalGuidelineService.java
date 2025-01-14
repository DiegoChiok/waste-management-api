package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Disposal Guidelines.
 * Provides basic CRUD operations and additional validation logic.
 */
public interface DisposalGuidelineService {
    /**
     * Creates and persists a new disposal guideline for a specific waste category.
     *
     * @param categoryId the ID of the associated waste category
     * @param guideline  the DisposalGuideline to create
     * @return the created DisposalGuideline with a generated ID
     */
    DisposalGuideline createGuideline(Long categoryId, DisposalGuideline guideline);

    /**
     * Retrieves a disposal guideline by its unique ID.
     *
     * @param id the ID of the guideline to fetch
     * @return an Optional containing the DisposalGuideline if found or empty if not
     */
    Optional<DisposalGuideline> getGuidelineById(Long id);

    /**
     * Retrieves all disposal guidelines.
     *
     * @return a list of all DisposalGuideline objects
     */
    List<DisposalGuideline> getAllGuidelines();

    /**
     * Updates an existing disposal guideline.
     *
     * @param id the ID of the guideline to update
     * @param guideline the updated DisposalGuideline data
     * @return the updated DisposalGuideline
     */
    DisposalGuideline updateGuideline(Long id, DisposalGuideline guideline);


    /**
     * Deletes a disposal guideline by its unique ID.
     *
     * @param id the ID of the guideline to delete
     */
    void deleteGuideline(Long id);

    /**
     * Validates the instructions for a disposal guideline.
     *
     * @param instructions the instructions to validate
     * @return true if the instructions meet the criteria, false otherwise
     */
    boolean isValidGuidelineInstructions(String instructions);

}
