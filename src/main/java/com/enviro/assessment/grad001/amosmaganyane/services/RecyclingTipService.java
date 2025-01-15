package com.enviro.assessment.grad001.amosmaganyane.services;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing RecyclingTip entities.
 * Provides methods for CRUD operations and validation of tip content.
 */
public interface RecyclingTipService {

    /**
     * Creates and persists a new recycling tip for a given waste category.
     *
     * @param categoryId the ID of the associated WasteCategory
     * @param tip the RecyclingTip to create
     * @return the created RecyclingTip with a generated ID
     */
    RecyclingTip createTip(Long categoryId, RecyclingTip tip);

    /**
     * Retrieves a recycling tip by its unique ID.
     *
     * @param id the ID of the tip to fetch
     * @return an Optional containing the RecyclingTip if found, or empty if not
     */
    Optional<RecyclingTip> getTipById(Long id);

    /**
     * Retrieves all recycling tips.
     *
     * @return a list of all RecyclingTip objects
     */
    List<RecyclingTip> getAllTips();

    /**
     * Updates an existing recycling tip.
     *
     * @param id the ID of the tip to update
     * @param tip the updated RecyclingTip data
     * @return the updated RecyclingTip
     */
    RecyclingTip updateTip(Long id, RecyclingTip tip);

    /**
     * Deletes a recycling tip by its unique ID.
     *
     * @param id the ID of the tip to delete
     */
    void deleteTip(Long id);

    /**
     * Validates the content of a recycling tip.
     *
     * @param content the content to validate
     * @return true if the content is valid, false otherwise
     */
    boolean isValidTipContent(String content);

    // Business operations
    /**
     * Retrieves all recycling tips associated with a specific waste category.
     *
     * @param categoryId the ID of the waste category
     * @return a list of recycling tips for the specified category
     */
    List<RecyclingTip> getTipsByCategory(Long categoryId);

    /**
     * Searches for recycling tips whose titles contain the given keyword (case-insensitive).
     * If the keyword is null or empty, retrieves all recycling tips.
     *
     * @param keyword the keyword to search for in tip titles
     * @return a list of recycling tips matching the keyword, or all tips if the keyword is empty
     */
    List<RecyclingTip> searchTips(String keyword);

    /**
     * Counts the number of recycling tips associated with a specific waste category.
     *
     * @param categoryId the ID of the waste category
     * @return the number of recycling tips in the specified category
     */
    int countTipsInCategory(Long categoryId);
}
