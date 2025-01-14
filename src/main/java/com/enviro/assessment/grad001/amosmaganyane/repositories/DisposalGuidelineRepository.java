package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisposalGuidelineRepository extends JpaRepository<DisposalGuideline, Long> {
    // Basic CRUD operations inherited from JpaRepository
    List<DisposalGuideline> findByTitleContainingIgnoreCase(String keyword);

}
