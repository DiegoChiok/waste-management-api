package com.enviro.assessment.grad001.amosmaganyane.models;

import java.util.Objects;

public class RecyclingTip {
    private final Long id;
    private final String title;
    private final String content;
    private final WasteCategory category;

    public RecyclingTip(Long id, String title, String content, WasteCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecyclingTip that = (RecyclingTip) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, category);
    }

    @Override
    public String toString() {
        return "RecyclingTip{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                '}';
    }


}
