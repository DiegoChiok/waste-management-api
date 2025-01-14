package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.services.DisposalGuidelineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(DisposalGuidelineController.class)
class DisposalGuidelineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DisposalGuidelineService guidelineService;

    @Autowired
    private ObjectMapper objectMapper;

    private WasteCategory testCategory;
    private DisposalGuideline testGuideline;

    @BeforeEach
    void initializeModels() {
        testCategory = new WasteCategory(1L, "Hazardous", "Description");
        testGuideline = new DisposalGuideline(1L, "Battery Disposal",
                "Proper steps for battery disposal", testCategory);
    }

    @Test
    void testCreateGuideline() throws Exception {
        when(guidelineService.createGuideline(eq(1L), any(DisposalGuideline.class)))
                .thenReturn(testGuideline);

        mockMvc.perform(post("/wastemanagementapi/categories/1/guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testGuideline)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Battery Disposal"));
    }

    @Test
    void testReturnBadRequestWhenCreatingInvalidGuideline() throws Exception {
        when(guidelineService.createGuideline(eq(1L), any(DisposalGuideline.class)))
                .thenThrow(new IllegalArgumentException("Invalid guideline"));

        mockMvc.perform(post("/wastemanagementapi/categories/1/guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testGuideline)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetGuidelineById() throws Exception {
        when(guidelineService.getGuidelineById(1L)).thenReturn(Optional.of(testGuideline));

        mockMvc.perform(get("/wastemanagementapi/guidelines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Battery Disposal"));
    }

    @Test
    void testReturn404WhenGuidelineNotFound() throws Exception {
        when(guidelineService.getGuidelineById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/wastemanagementapi/guidelines/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllGuidelines() throws Exception {
        List<DisposalGuideline> guidelines = List.of(
                testGuideline,
                new DisposalGuideline(2L, "Chemical Disposal",
                        "Safe chemical disposal steps", testCategory)
        );
        when(guidelineService.getAllGuidelines()).thenReturn(guidelines);

        mockMvc.perform(get("/wastemanagementapi/guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Battery Disposal"))
                .andExpect(jsonPath("$[1].title").value("Chemical Disposal"));
    }

    @Test
    void testUpdateGuideline() throws Exception {
        DisposalGuideline updatedGuideline = new DisposalGuideline(1L,
                "Updated Battery Disposal",
                "Updated disposal instructions", testCategory);
        when(guidelineService.updateGuideline(eq(1L), any(DisposalGuideline.class)))
                .thenReturn(updatedGuideline);

        mockMvc.perform(put("/wastemanagementapi/guidelines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGuideline)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Battery Disposal"));
    }

    @Test
    void testDeleteGuideline() throws Exception {
        mockMvc.perform(delete("/wastemanagementapi/guidelines/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetGuidelinesByCategory() throws Exception {
        List<DisposalGuideline> guidelines = List.of(testGuideline);
        when(guidelineService.getGuidelinesByCategory(1L)).thenReturn(guidelines);

        mockMvc.perform(get("/wastemanagementapi/categories/1/guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Battery Disposal"));
    }
}