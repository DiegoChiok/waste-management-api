package com.enviro.assessment.grad001.amosmaganyane.models;

import jakarta.persistence.*;
import java.util.Objects;

// Represents a recycling tip entity with many-to-one relationship to WasteCategory
@Entity
@Table(name = "recycling_tips")
public class RecyclingTip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Title and content are required fields to make recycling tips meaningful
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    //Each recycling tip belongs to a specific waste category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private WasteCategory category;

    public RecyclingTip() {
    }

    public RecyclingTip(Long id, String title, String content, WasteCategory category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WasteCategory getCategory(){
        return category;
    }

    public void setCategory(WasteCategory category) {
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
