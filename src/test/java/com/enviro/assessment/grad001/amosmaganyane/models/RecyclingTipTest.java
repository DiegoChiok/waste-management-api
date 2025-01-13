package com.enviro.assessment.grad001.amosmaganyane.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecyclingTipTest {

    @Test
    void testCreateRecyclingTipWithValidData() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        RecyclingTip tip = new RecyclingTip(5L, "Paper Recycling",
                "How to recycle paper", category);

        assertEquals(5L, tip.getId());
        assertEquals("Paper Recycling", tip.getTitle());
        assertEquals("How to recycle paper", tip.getContent());
        assertEquals(category, tip.getCategory());
    }

    @Test
    void testEqualRecyclingTipsShouldBeEqual() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        RecyclingTip tip1 = new RecyclingTip(1L, "Paper Recycling",
                "Content", category);
        RecyclingTip tip2 = new RecyclingTip(1L, "Paper Recycling",
                "Content", category);

        assertEquals(tip1, tip2);
        assertEquals(tip1.hashCode(), tip2.hashCode());
    }

    @Test
    void testDifferentRecyclingTipsShouldNotBeEqual() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        RecyclingTip tip1 = new RecyclingTip(1L, "Paper Recycling",
                "Content", category);
        RecyclingTip tip2 = new RecyclingTip(2L, "Glass Recycling",
                "Different content", category);

        assertNotEquals(tip1, tip2);
        assertNotEquals(tip1.hashCode(), tip2.hashCode());
    }

    @Test
    void testToString() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        RecyclingTip tip = new RecyclingTip(1L, "Paper Recycling",
                "Content", category);
        String expected = "RecyclingTip{id=1, title='Paper Recycling', " +
                "content='Content', category=" + category + "}";

        assertEquals(expected, tip.toString());
    }
}