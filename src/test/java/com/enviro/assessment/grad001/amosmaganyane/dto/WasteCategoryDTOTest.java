package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WasteCategoryDTOTest {

    /**
     * Verifies the conversion of a WasteCategory entity to its corresponding DTO.
     */
    @Test
    void testConvertEntityToDTO() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");

        WasteCategoryDTO dto = WasteCategoryDTO.fromEntity(category);

        assertEquals(1L, dto.getId());
        assertEquals("Recyclable", dto.getName());
        assertEquals("Description", dto.getDescription());
    }

    /**
     * Verifies handling of null values in WasteCategory entity during DTO conversion.
     */
    @Test
    void testHandleNullValues() {
        WasteCategory category = new WasteCategory(null, "Recyclable", null);

        WasteCategoryDTO dto = WasteCategoryDTO.fromEntity(category);

        assertNull(dto.getId());
        assertEquals("Recyclable", dto.getName());
        assertNull(dto.getDescription());
    }
}
