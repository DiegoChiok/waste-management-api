package com.enviro.assessment.grad001.amosmaganyane.repositories;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingTipRepository extends JpaRepository<RecyclingTip, Long> {
    // Basic CRUD operations inherited from JpaRepository
}
