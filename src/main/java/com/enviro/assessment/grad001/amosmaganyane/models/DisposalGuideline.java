package com.enviro.assessment.grad001.amosmaganyane.models;

import java.util.Objects;

public class DisposalGuideline {
    private final Long id;
    private final String title;
    private final String instructions;
    private final WasteCategory category;

    public DisposalGuideline (Long id, String title, String instructions, WasteCategory category){
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public WasteCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisposalGuideline that = (DisposalGuideline) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(instructions, that.instructions) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, instructions, category);
    }

    @Override
    public String toString() {
        return "DisposalGuideline{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", instructions='" + instructions + '\'' +
                ", category=" + category +
                '}';
    }

}
