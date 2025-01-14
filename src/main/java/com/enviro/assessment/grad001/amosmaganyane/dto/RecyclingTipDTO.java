package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;

public class RecyclingTipDTO {
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
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