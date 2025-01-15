package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.services.WasteCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WasteCategoryController.class)
@DisplayName("Waste Category API Tests")
class WasteCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WasteCategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private WasteCategory testCategory;

    @BeforeEach
    void initializeCategory() {
        testCategory = new WasteCategory(1L, "Recyclable", "Items that can be recycled");
    }

    @Test
    @DisplayName("POST /categories - Should create a new waste category")
    void testCreateCategory() throws Exception {
        when(categoryService.createCategory(any(WasteCategory.class))).thenReturn(testCategory);

        mockMvc.perform(post("/wastemanagementapi/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Recyclable"));
    }

    @Test
    @DisplayName("GET /categories/{id} - Should return a category when it exists")
    void testGetCategoryById() throws Exception {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(testCategory));

        mockMvc.perform(get("/wastemanagementapi/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Recyclable"));
    }

    @Test
    @DisplayName("GET /categories/{id} - Should return 404 when category not found")
    void testReturn404WhenCategoryNotFound() throws Exception {
        when(categoryService.getCategoryById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/wastemanagementapi/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /categories - Should return all categories")
    void testGetAllCategories() throws Exception {
        List<WasteCategory> categories = List.of(
                testCategory,
                new WasteCategory(2L, "Organic", "Biodegradable waste")
        );
        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/wastemanagementapi/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Recyclable"))
                .andExpect(jsonPath("$[1].name").value("Organic"));
    }

    @Test
    @DisplayName("PUT /categories/{id} - Should update an existing category")
    void testUpdateCategory() throws Exception {
        WasteCategory updatedCategory = new WasteCategory(1L, "Updated Name", "Updated description");
        when(categoryService.updateCategory(eq(1L), any(WasteCategory.class)))
                .thenReturn(updatedCategory);

        mockMvc.perform(put("/wastemanagementapi/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    @DisplayName("DELETE /categories/{id} - Should delete a category")
    void testDeleteCategory() throws Exception {
        mockMvc.perform(delete("/wastemanagementapi/categories/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /categories/search - Should return categories matching search criteria")
    void testSearchCategories() throws Exception {
        String keyword = "Recyclable";
        List<WasteCategory> searchResults = List.of(testCategory);
        when(categoryService.searchCategories(keyword)).thenReturn(searchResults);

        mockMvc.perform(get("/wastemanagementapi/categories/search")
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Recyclable"));
    }

}
