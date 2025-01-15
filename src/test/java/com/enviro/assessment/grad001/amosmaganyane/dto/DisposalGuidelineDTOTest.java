package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.DisposalGuideline;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisposalGuidelineDTOTest {

    /**
     * Verifies the conversion of a DisposalGuideline entity to its corresponding DTO.
     */
    @Test
    void testConvertEntityToDTO() {
        WasteCategory category = new WasteCategory(1L, "Hazardous", "Description");
        DisposalGuideline guideline = new DisposalGuideline(2L, "Battery Disposal",
                "Safe disposal instructions", category);

        DisposalGuidelineDTO dto = DisposalGuidelineDTO.fromEntity(guideline);

        assertEquals(2L, dto.getId());
        assertEquals("Battery Disposal", dto.getTitle());
        assertEquals("Safe disposal instructions", dto.getInstructions());
        assertEquals(1L, dto.getCategoryId());
        assertEquals("Hazardous", dto.getCategoryName());
    }

    /**
     * Verifies exception handling when a null category is encountered during DTO conversion.
     */
    @Test
    void testHandleNullCategory() {
        DisposalGuideline guideline = new DisposalGuideline(1L, "Title",
                "Instructions", null);

        assertThrows(NullPointerException.class,
                () -> DisposalGuidelineDTO.fromEntity(guideline));
    }
}