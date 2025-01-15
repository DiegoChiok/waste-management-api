package com.enviro.assessment.grad001.amosmaganyane.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DisposalGuidelineTest {

    /**
     * Verifies the creation of a DisposalGuideline object with valid data.
     */
    @Test
    void testCreateDisposalGuidelineWithValidData() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        DisposalGuideline guideline = new DisposalGuideline(5L, "Paper Disposal",
                "How to dispose paper", category);

        assertEquals(5L, guideline.getId());
        assertEquals("Paper Disposal", guideline.getTitle());
        assertEquals("How to dispose paper", guideline.getInstructions());
        assertEquals(category, guideline.getCategory());
    }

    /**
     * Verifies that DisposalGuideline objects with the same data are equal.
     */
    @Test
    void testEqualDisposalGuidelinesShouldBeEqual() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        DisposalGuideline guideline1 = new DisposalGuideline(1L, "Paper Disposal",
                "Instructions", category);
        DisposalGuideline guideline2 = new DisposalGuideline(1L, "Paper Disposal",
                "Instructions", category);

        assertEquals(guideline1, guideline2);
        assertEquals(guideline1.hashCode(), guideline2.hashCode());
    }

    /**
     * Verifies that DisposalGuideline objects with different data are not equal.
     */
    @Test
    void testDifferentDisposalGuidelinesShouldNotBeEqual() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        DisposalGuideline guideline1 = new DisposalGuideline(1L, "Paper Disposal",
                "Instructions", category);
        DisposalGuideline guideline2 = new DisposalGuideline(2L, "Glass Disposal",
                "Different instructions", category);

        assertNotEquals(guideline1, guideline2);
        assertNotEquals(guideline1.hashCode(), guideline2.hashCode());
    }

    /**
     * Verifies the toString method of the DisposalGuideline class.
     */
    @Test
    void testToString() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        DisposalGuideline guideline = new DisposalGuideline(1L, "Paper Disposal",
                "Instructions", category);
        String expected = "DisposalGuideline{id=1, title='Paper Disposal', " +
                "instructions='Instructions', category=" + category + "}";

        assertEquals(expected, guideline.toString());
    }
}