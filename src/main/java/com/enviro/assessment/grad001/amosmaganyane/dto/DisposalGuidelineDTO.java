package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;

public class DisposalGuidelineDTO {
    private Long id;
    private String title;
    private String instructions;
    private Long categoryId;
    private String categoryName;

    public DisposalGuidelineDTO() {}

    public DisposalGuidelineDTO(Long id, String title, String instructions,
                                Long categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static DisposalGuidelineDTO fromEntity(DisposalGuideline guideline) {
        return new DisposalGuidelineDTO(
                guideline.getId(),
                guideline.getTitle(),
                guideline.getInstructions(),
                guideline.getCategory().getId(),
                guideline.getCategory().getName()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}