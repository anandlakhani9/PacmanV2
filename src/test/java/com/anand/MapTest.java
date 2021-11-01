package com.anand;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {


    private Map testMap = new Map(5,5,"src/test/java/com/anand/TestMap.csv");
    // Todo: error case - array too small!
    //not sure how to implement that though
    //    private Map testMap2 = new Map(4,4,"src/test/java/TestMap.csv");
    @Test
    void initMap(){
        //when
        String actual[][] = testMap.getMapArray();
        //check the array is read in correctly
        String[][] expected = {
                {"TL", "HW", "HW", "HW", "TR"},
                {"VW", "II", "ES", "II", "VW"},
                {"VW", "ES", "ES", "ES", "VW"},
                {"VW", "IE", "ES", "IP", "VW"},
                {"BL", "HW", "HW", "HW", "BR"}
        };
        assertArrayEquals(actual,expected);
    }

    @Test
    void blocked() {
        //check all walls are blocked
        assertTrue(testMap.blocked(0,0));
        assertTrue(testMap.blocked(0,1));
        assertTrue(testMap.blocked(0,2));
        assertTrue(testMap.blocked(0,3));
        assertTrue(testMap.blocked(0,4));
        assertTrue(testMap.blocked(1,0));
        assertTrue(testMap.blocked(2,0));
        assertTrue(testMap.blocked(3,0));
        assertTrue(testMap.blocked(4,0));
        assertTrue(testMap.blocked(4,1));
        assertTrue(testMap.blocked(4,2));
        assertTrue(testMap.blocked(4,3));
        assertTrue(testMap.blocked(4,4));
        assertTrue(testMap.blocked(1,4));
        assertTrue(testMap.blocked(2,4));
        assertTrue(testMap.blocked(3,4));
        //check other stuff isn't blocked
        assertFalse(testMap.blocked(1,1));
        assertFalse(testMap.blocked(1,2));
        assertFalse(testMap.blocked(1,3));
        assertFalse(testMap.blocked(2,1));
        assertFalse(testMap.blocked(2,2));
        assertFalse(testMap.blocked(2,3));
        assertFalse(testMap.blocked(3,1));
        assertFalse(testMap.blocked(3,2));
        assertFalse(testMap.blocked(3,3));
    }

    @Test
    void intersection() {
        assertTrue(testMap.intersection(1,1));
        assertTrue(testMap.intersection(1,3));
        assertTrue(testMap.intersection(3,1));
        assertTrue(testMap.intersection(3,3));

        assertFalse(testMap.intersection(0,0));
        assertFalse(testMap.intersection(0,1));
        assertFalse(testMap.intersection(0,2));
        assertFalse(testMap.intersection(0,3));
        assertFalse(testMap.intersection(0,4));
        assertFalse(testMap.intersection(1,0));
        assertFalse(testMap.intersection(1,2));
        assertFalse(testMap.intersection(1,4));
        assertFalse(testMap.intersection(2,0));
        assertFalse(testMap.intersection(2,1));
        assertFalse(testMap.intersection(2,2));
        assertFalse(testMap.intersection(2,3));
        assertFalse(testMap.intersection(2,4));
        assertFalse(testMap.intersection(3,0));
        assertFalse(testMap.intersection(3,2));
        assertFalse(testMap.intersection(3,4));
        assertFalse(testMap.intersection(4,0));
        assertFalse(testMap.intersection(4,1));
        assertFalse(testMap.intersection(4,2));
        assertFalse(testMap.intersection(4,3));
        assertFalse(testMap.intersection(4,4));
    }

    @Test
    void getTile() {
    }

    @Test
    void setTile() {
    }

    @Test
    void getMapArray() {
    }
}