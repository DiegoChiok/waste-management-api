package com.enviro.assessment.grad001.amosmaganyane.models;

import java.util.Objects;

public class WasteCategory {
    private final Long id;
    private final String name;
    private final String description;


    public WasteCategory(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WasteCategory that = (WasteCategory) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "WasteCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
