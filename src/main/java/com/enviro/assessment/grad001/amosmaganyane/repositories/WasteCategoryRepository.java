package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WasteCategoryRepository extends JpaRepository<WasteCategory, Long> {
    
    // Basic CRUD operations inherited from JpaRepository

    /**
     * Checks if a category with the given name exists (case-insensitive).
     *
     * @param name the name to check
     * @return true if a category with the name exists, false otherwise
     */
    boolean existsByNameIgnoreCase(String name);


    /**
     * Finds categories with names containing the given keyword (case-insensitive).
     *
     * @param keyword the keyword to search for
     * @return a list of matching WasteCategory objects
     */
    List<WasteCategory> findByNameContainingIgnoreCase(String keyword);
}
