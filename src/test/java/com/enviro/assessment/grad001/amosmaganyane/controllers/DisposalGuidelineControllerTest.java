package com.enviro.assessment.grad001.amosmaganyane.controllers;

import com.enviro.assessment.grad001.amosmaganyane.dto.DisposalGuidelineDTO;
import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import com.enviro.assessment.grad001.amosmaganyane.services.DisposalGuidelineService;
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

@WebMvcTest(DisposalGuidelineController.class)
@DisplayName("Disposal Guidelines API Tests")
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
    @DisplayName("POST /categories/{categoryId}/guidelines - Should create a new disposal guideline")
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
    @DisplayName("POST /categories/{categoryId}/guidelines - Should return 400 for invalid guideline data")
    void testReturnBadRequestWhenCreatingInvalidGuideline() throws Exception {
        when(guidelineService.createGuideline(eq(1L), any(DisposalGuideline.class)))
                .thenThrow(new IllegalArgumentException("Invalid guideline"));

        mockMvc.perform(post("/wastemanagementapi/categories/1/guidelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testGuideline)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /guidelines/{id} - Should return a guideline when it exists")
    void testGetGuidelineById() throws Exception {
        when(guidelineService.getGuidelineById(1L)).thenReturn(Optional.of(testGuideline));

        mockMvc.perform(get("/wastemanagementapi/guidelines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Battery Disposal"));
    }

    @Test
    @DisplayName("GET /guidelines/{id} - Should return 404 when guideline not found")
    void testReturn404WhenGuidelineNotFound() throws Exception {
        when(guidelineService.getGuidelineById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/wastemanagementapi/guidelines/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /guidelines - Should return all disposal guidelines")
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
    @DisplayName("PUT /guidelines/{id} - Should update an existing guideline")
    void testUpdateGuideline() throws Exception {
        Long guidelineId = 1L;
        DisposalGuidelineDTO updateRequest = new DisposalGuidelineDTO();
        updateRequest.setTitle("Updated Battery Disposal");
        updateRequest.setInstructions("Updated disposal instructions");

        DisposalGuideline existingGuideline = new DisposalGuideline(guidelineId,
                "Old Title", "Old instructions", testCategory);
        DisposalGuideline updatedGuideline = new DisposalGuideline(guidelineId,
                "Updated Battery Disposal",
                "Updated disposal instructions",
                testCategory);

        when(guidelineService.getGuidelineById(guidelineId))
                .thenReturn(Optional.of(existingGuideline));
        when(guidelineService.updateGuideline(eq(guidelineId), any(DisposalGuideline.class)))
                .thenReturn(updatedGuideline);

        mockMvc.perform(put("/wastemanagementapi/guidelines/" + guidelineId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guidelineId))
                .andExpect(jsonPath("$.title").value("Updated Battery Disposal"))
                .andExpect(jsonPath("$.instructions").value("Updated disposal instructions"))
                .andExpect(jsonPath("$.categoryId").value(testCategory.getId()))
                .andExpect(jsonPath("$.categoryName").value(testCategory.getName()));
    }

    @Test
    @DisplayName("PUT /guidelines/{id} - Should return 404 when guideline not found")
    void testUpdateNonExistentGuideline() throws Exception {
        Long nonExistentId = 999L;
        DisposalGuidelineDTO updateRequest = new DisposalGuidelineDTO();
        updateRequest.setTitle("Updated Title");
        updateRequest.setInstructions("Updated instructions");

        when(guidelineService.getGuidelineById(nonExistentId))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/wastemanagementapi/guidelines/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("DELETE /guidelines/{id} - Should delete a guideline")
    void testDeleteGuideline() throws Exception {
        mockMvc.perform(delete("/wastemanagementapi/guidelines/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /categories/{categoryId}/guidelines - Should return guidelines for a category")
    void shouldGetGuidelinesByCategory() throws Exception {
        List<DisposalGuideline> guidelines = List.of(testGuideline);
        when(guidelineService.getGuidelinesByCategory(1L)).thenReturn(guidelines);

        mockMvc.perform(get("/wastemanagementapi/categories/1/guidelines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Battery Disposal"));
    }

    @Test
    @DisplayName("GET /guidelines/search - Should return guidelines matching search keyword")
    void testSearchGuidelines() throws Exception {
        String keyword = "battery";
        List<DisposalGuideline> searchResults = List.of(
                new DisposalGuideline(1L, "Battery Disposal",
                        "Instructions for batteries", testCategory),
                new DisposalGuideline(2L, "Car Battery Guidelines",
                        "Car battery disposal steps", testCategory)
        );
        when(guidelineService.searchGuidelines(keyword)).thenReturn(searchResults);

        mockMvc.perform(get("/wastemanagementapi/guidelines/search")
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Battery Disposal"))
                .andExpect(jsonPath("$[1].title").value("Car Battery Guidelines"));
    }

    @Test
    @DisplayName("GET /guidelines/search - Should return all guidelines when keyword is empty")
    void testSearchGuidelinesWithEmptyKeyword() throws Exception {
        List<DisposalGuideline> allGuidelines = List.of(
                new DisposalGuideline(1L, "Battery Disposal",
                        "Instructions 1", testCategory),
                new DisposalGuideline(2L, "Chemical Disposal",
                        "Instructions 2", testCategory)
        );
        when(guidelineService.searchGuidelines("")).thenReturn(allGuidelines);

        mockMvc.perform(get("/wastemanagementapi/guidelines/search")
                        .param("keyword", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /guidelines/search - Should return empty list when no matches found")
    void testSearchGuidelinesNoMatches() throws Exception {
        String keyword = "nonexistent";
        when(guidelineService.searchGuidelines(keyword)).thenReturn(List.of());

        mockMvc.perform(get("/wastemanagementapi/guidelines/search")
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}