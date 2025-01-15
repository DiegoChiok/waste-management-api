package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for Waste Categories")
public class WasteCategoryDTO {

    @Schema(example = "1",
            description = "Unique identifier of the waste category")
    private Long id;

    @Schema(example = "Recyclable Materials",
            description = "Name of the waste category")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Schema(example = "Materials that can be processed and reused, including paper, glass, and plastics",
            description = "Detailed description of the waste category")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Schema(example = "0",
            description = "Number of disposal guidelines associated with this category")
    private int guidelinesCount;

    @Schema(example = "0",
            description = "Number of recycling tips associated with this category")
    private int tipsCount;

    public WasteCategoryDTO() {}

    public WasteCategoryDTO(Long id, String name, String description,
                            int guidelinesCount, int tipsCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.guidelinesCount = guidelinesCount;
        this.tipsCount = tipsCount;
    }

    @Schema(hidden = true)
    public static WasteCategoryDTO fromEntity(WasteCategory category) {
        return new WasteCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getGuidelines().size(),
                category.getRecyclingTips().size()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGuidelinesCount() {
        return guidelinesCount;
    }

    public void setGuidelinesCount(int guidelinesCount) {
        this.guidelinesCount = guidelinesCount;
    }

    public int getTipsCount() {
        return tipsCount;
    }

    public void setTipsCount(int tipsCount) {
        this.tipsCount = tipsCount;
    }
}