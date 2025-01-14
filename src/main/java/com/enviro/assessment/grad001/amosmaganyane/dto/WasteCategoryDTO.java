package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;

public class WasteCategoryDTO {
    private Long id;
    private String name;
    private String description;

    public WasteCategoryDTO() {}

    public WasteCategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static WasteCategoryDTO fromEntity(WasteCategory category) {
        return new WasteCategoryDTO(category.getId(), category.getName(), category.getDescription());
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


}