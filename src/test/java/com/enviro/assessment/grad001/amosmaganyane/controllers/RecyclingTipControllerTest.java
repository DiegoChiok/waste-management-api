package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.services.RecyclingTipService;
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

@WebMvcTest(RecyclingTipController.class)
@DisplayName("Recycling Tips API Tests")
class RecyclingTipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecyclingTipService tipService;

    @Autowired
    private ObjectMapper objectMapper;

    private WasteCategory testCategory;
    private RecyclingTip testTip;

    @BeforeEach
    void initializeModels() {
        testCategory = new WasteCategory(1L, "Recyclable", "Description");
        testTip = new RecyclingTip(1L, "Paper Recycling",
                "How to recycle paper properly", testCategory);
    }

    @Test
    @DisplayName("POST /categories/{categoryId}/tips - Should create a new recycling tip")
    void testCreateTip() throws Exception {
        when(tipService.createTip(eq(1L), any(RecyclingTip.class))).thenReturn(testTip);

        mockMvc.perform(post("/wastemanagementapi/categories/1/tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTip)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Paper Recycling"));
    }

    @Test
    @DisplayName("POST /categories/{categoryId}/tips - Should return 400 for invalid tip data")
    void testReturnBadRequestWhenCreatingInvalidTip() throws Exception {
        when(tipService.createTip(eq(1L), any(RecyclingTip.class)))
                .thenThrow(new IllegalArgumentException("Invalid tip"));

        mockMvc.perform(post("/wastemanagementapi/categories/1/tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTip)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /tips/{id} - Should return a tip when it exists")
    void testGetTipById() throws Exception {
        when(tipService.getTipById(1L)).thenReturn(Optional.of(testTip));

        mockMvc.perform(get("/wastemanagementapi/tips/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Paper Recycling"));
    }

    @Test
    @DisplayName("GET /tips/{id} - Should return 404 when tip not found")
    void testReturn404WhenTipNotFound() throws Exception {
        when(tipService.getTipById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/wastemanagementapi/tips/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /tips - Should return all recycling tips")
    void testGetAllTips() throws Exception {
        List<RecyclingTip> tips = List.of(
                testTip,
                new RecyclingTip(2L, "Glass Recycling", "How to recycle glass", testCategory)
        );
        when(tipService.getAllTips()).thenReturn(tips);

        mockMvc.perform(get("/wastemanagementapi/tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Paper Recycling"))
                .andExpect(jsonPath("$[1].title").value("Glass Recycling"));
    }

    @Test
    @DisplayName("PUT /tips/{id} - Should update an existing tip")
    void testUpdateTip() throws Exception {
        RecyclingTip updatedTip = new RecyclingTip(1L, "Updated Title",
                "Updated content", testCategory);
        when(tipService.updateTip(eq(1L), any(RecyclingTip.class)))
                .thenReturn(updatedTip);

        mockMvc.perform(put("/wastemanagementapi/tips/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    @DisplayName("DELETE /tips/{id} - Should delete a tip")
    void testDeleteTip() throws Exception {
        mockMvc.perform(delete("/wastemanagementapi/tips/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /categories/{categoryId}/tips - Should return tips for a category")
    void shouldGetTipsByCategory() throws Exception {
        List<RecyclingTip> tips = List.of(testTip);
        when(tipService.getTipsByCategory(1L)).thenReturn(tips);

        mockMvc.perform(get("/wastemanagementapi/categories/1/tips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Paper Recycling"));
    }

}