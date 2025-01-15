package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Recycling Tips")
public class RecyclingTipDTO {

    @Schema(example = "1",
            description = "Unique identifier of the recycling tip")
    private Long id;

    @Schema(example = "Paper Recycling Guide",
            description = "Title of the recycling tip")
    private String title;

    @Schema(example = "1. Separate paper by type\n2. Remove staples and clips\n3. Keep paper dry and clean",
            description = "Detailed recycling instructions and helpful information")
    private String content;

    @Schema(example = "1",
            description = "ID of the waste category this tip belongs to")
    private Long categoryId;

    @Schema(example = "Recyclable Materials",
            description = "Name of the waste category this tip belongs to")
    private String categoryName;

    public RecyclingTipDTO() {}

    public RecyclingTipDTO(Long id, String title, String content,
                           Long categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Schema(hidden = true)
    public static RecyclingTipDTO fromEntity(RecyclingTip tip) {
        return new RecyclingTipDTO(
                tip.getId(),
                tip.getTitle(),
                tip.getContent(),
                tip.getCategory().getId(),
                tip.getCategory().getName()
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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