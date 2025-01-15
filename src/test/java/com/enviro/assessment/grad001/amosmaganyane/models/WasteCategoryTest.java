package com.enviro.assessment.grad001.amosmaganyane.models;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WasteCategoryTest {


    /**
     * Verifies the creation of a WasteCategory object with valid data.
     */
    @Test
    void testCreateWasteCategoryWithValidData(){
        WasteCategory category = new WasteCategory(5L,"Recyclable",
                "Items that can be recycled");

        assertEquals(category.getId(),5L);
        assertEquals(category.getDescription(), "Items that can be recycled");
        assertTrue(category.getRecyclingTips().isEmpty());
        assertTrue(category.getGuidelines().isEmpty());
    }

    /**
     * Verifies that WasteCategory objects with the same data are equal.
     */
    @Test
    void testEqualWasteCategoriesShouldBeEqual() {
        WasteCategory category1 = new WasteCategory(1L, "Recyclable", "Description");
        WasteCategory category2 = new WasteCategory(1L, "Recyclable", "Description");

        assertEquals(category1, category2);
        assertEquals(category1.hashCode(), category2.hashCode());
    }

    /**
     * Verifies that WasteCategory objects with different data are not equal.
     */
    @Test
    void testDifferentWasteCategoriesShouldNotBeEqual() {
        WasteCategory category1 = new WasteCategory(1L, "Recyclable", "Description");
        WasteCategory category2 = new WasteCategory(2L, "Organic", "Different description");

        assertNotEquals(category1, category2);
        assertNotEquals(category1.hashCode(), category2.hashCode());
    }

    /**
     * Verifies the toString method of the WasteCategory class.
     */
    @Test
    void testToString() {
        WasteCategory category = new WasteCategory(1L, "Recyclable", "Description");
        String expected = "WasteCategory{id=1, name='Recyclable', description='Description'}";

        assertEquals(expected, category.toString());
    }
}
