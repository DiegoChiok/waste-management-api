package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Disposal Guidelines")
public class DisposalGuidelineDTO {

    @Schema(example = "1",
            description = "Unique identifier of the disposal guideline")
    private Long id;

    @Schema(example = "Battery Disposal",
            description = "Title of the disposal guideline")
    private String title;

    @Schema(example = "1. Remove batteries from device\n2." +
            " Store in dry container\n3. Take to recycling center",
            description = "Detailed step-by-step disposal instructions")
    private String instructions;

    @Schema(example = "1",
            description = "ID of the waste category this guideline belongs to")
    private Long categoryId;

    @Schema(example = "Hazardous Waste",
            description = "Name of the waste category this guideline belongs to")
    private String categoryName;

    public DisposalGuidelineDTO() {}

    // Constructor with fields
    public DisposalGuidelineDTO(Long id, String title, String instructions,
                                Long categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Schema(hidden = true)
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