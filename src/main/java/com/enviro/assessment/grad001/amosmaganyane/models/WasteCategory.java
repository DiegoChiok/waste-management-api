package com.enviro.assessment.grad001.amosmaganyane.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a waste category with its associated recycling tips and disposal guidelines
@Entity
@Table(name = "waste_categories")
public class WasteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name is required as it identifies the category type
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    // One category can have multiple recycling tips
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecyclingTip> recyclingTips;

    // One category can have multiple disposal guidelines
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisposalGuideline> guidelines;

    public WasteCategory() {
        this.recyclingTips = new ArrayList<>();
        this.guidelines = new ArrayList<>();
    }

    public WasteCategory(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.recyclingTips = new ArrayList<>();
        this.guidelines = new ArrayList<>();

    }
    public Long getId() {
        return id;
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

    public List<RecyclingTip> getRecyclingTips(){
        return List.copyOf(recyclingTips);
    }

    public List<DisposalGuideline> getGuidelines(){
        return List.copyOf(guidelines);
    }

    public void addRecyclingTip(RecyclingTip tip) {
        recyclingTips.add(tip);
    }

    public void addGuideline(DisposalGuideline guideline) {
        guidelines.add(guideline);
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
