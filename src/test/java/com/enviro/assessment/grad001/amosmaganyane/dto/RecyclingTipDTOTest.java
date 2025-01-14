package com.enviro.assessment.grad001.amosmaganyane.dto;

import com.enviro.assessment.grad001.amosmaganyane.models.RecyclingTip;
import com.enviro.assessment.grad001.amosmaganyane.models.WasteCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecyclingTipDTOTest {

    @Test
    void testConvertEntityToDTO() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        RecyclingTip tip = new RecyclingTip(2L, "Paper Recycling",
                "How to recycle paper", category);

        RecyclingTipDTO dto = RecyclingTipDTO.fromEntity(tip);

        assertEquals(2L, dto.getId());
        assertEquals("Paper Recycling", dto.getTitle());
        assertEquals("How to recycle paper", dto.getContent());
        assertEquals(1L, dto.getCategoryId());
        assertEquals("Recyclable", dto.getCategoryName());
    }

    @Test
    void testHandleNullCategory() {
        RecyclingTip tip = new RecyclingTip(1L, "Title", "Content", null);

        assertThrows(NullPointerException.class,
                () -> RecyclingTipDTO.fromEntity(tip));
    }
}