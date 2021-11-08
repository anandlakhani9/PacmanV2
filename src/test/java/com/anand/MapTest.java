package com.anand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    private Map testMap;

    @BeforeEach
    void createTestMap(){
        testMap = new Map(5,5,"src/test/java/com/anand/TestMap.csv");
    }

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
                {"VW", "II", "DD", "II", "VW"},
                {"T1", "ES", "DD", "ES", "T2"},
                {"VW", "IE", "PP", "IP", "VW"},
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
        assertTrue(testMap.blocked(3,0));
        assertTrue(testMap.blocked(4,0));
        assertTrue(testMap.blocked(4,1));
        assertTrue(testMap.blocked(4,2));
        assertTrue(testMap.blocked(4,3));
        assertTrue(testMap.blocked(4,4));
        assertTrue(testMap.blocked(1,4));
        assertTrue(testMap.blocked(3,4));
        //check other stuff isn't blocked
        assertFalse(testMap.blocked(1,1));
        assertFalse(testMap.blocked(1,2));
        assertFalse(testMap.blocked(1,3));
        assertFalse(testMap.blocked(2,0));
        assertFalse(testMap.blocked(2,1));
        assertFalse(testMap.blocked(2,2));
        assertFalse(testMap.blocked(2,3));
        assertFalse(testMap.blocked(2,4));
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

    @Test
    void t1() {
        assertTrue(testMap.t1(2,0));

        assertFalse(testMap.t1(0,0));
        assertFalse(testMap.t1(0,1));
        assertFalse(testMap.t1(0,2));
        assertFalse(testMap.t1(0,3));
        assertFalse(testMap.t1(0,4));
        assertFalse(testMap.t1(1,0));
        assertFalse(testMap.t1(1,1));
        assertFalse(testMap.t1(1,2));
        assertFalse(testMap.t1(1,3));
        assertFalse(testMap.t1(1,4));
        assertFalse(testMap.t1(2,1));
        assertFalse(testMap.t1(2,2));
        assertFalse(testMap.t1(2,3));
        assertFalse(testMap.t1(2,4));
        assertFalse(testMap.t1(3,0));
        assertFalse(testMap.t1(3,1));
        assertFalse(testMap.t1(3,2));
        assertFalse(testMap.t1(3,3));
        assertFalse(testMap.t1(3,4));
        assertFalse(testMap.t1(4,0));
        assertFalse(testMap.t1(4,1));
        assertFalse(testMap.t1(4,2));
        assertFalse(testMap.t1(4,3));
        assertFalse(testMap.t1(4,4));
    }

    @Test
    void t2() {
        assertTrue(testMap.t2(2,4));

        assertFalse(testMap.t2(0,0));
        assertFalse(testMap.t2(0,1));
        assertFalse(testMap.t2(0,2));
        assertFalse(testMap.t2(0,3));
        assertFalse(testMap.t2(0,4));
        assertFalse(testMap.t2(1,0));
        assertFalse(testMap.t2(1,1));
        assertFalse(testMap.t2(1,2));
        assertFalse(testMap.t2(1,3));
        assertFalse(testMap.t2(1,4));
        assertFalse(testMap.t2(2,0));
        assertFalse(testMap.t2(2,1));
        assertFalse(testMap.t2(2,2));
        assertFalse(testMap.t2(2,3));
        assertFalse(testMap.t2(3,0));
        assertFalse(testMap.t2(3,1));
        assertFalse(testMap.t2(3,2));
        assertFalse(testMap.t2(3,3));
        assertFalse(testMap.t2(3,4));
        assertFalse(testMap.t2(4,0));
        assertFalse(testMap.t2(4,1));
        assertFalse(testMap.t2(4,2));
        assertFalse(testMap.t2(4,3));
        assertFalse(testMap.t2(4,4));
    }

    @Test
    void pellet() {
        assertTrue(testMap.pellet(1,2));
        assertTrue(testMap.pellet(2,2));

        assertFalse(testMap.pellet(0,0));
        assertFalse(testMap.pellet(0,1));
        assertFalse(testMap.pellet(0,2));
        assertFalse(testMap.pellet(0,3));
        assertFalse(testMap.pellet(0,4));
        assertFalse(testMap.pellet(1,0));
        assertFalse(testMap.pellet(1,1));
        assertFalse(testMap.pellet(1,3));
        assertFalse(testMap.pellet(1,4));
        assertFalse(testMap.pellet(2,0));
        assertFalse(testMap.pellet(2,1));
        assertFalse(testMap.pellet(2,3));
        assertFalse(testMap.pellet(2,4));
        assertFalse(testMap.pellet(3,0));
        assertFalse(testMap.pellet(3,1));
        assertFalse(testMap.pellet(3,2));
        assertFalse(testMap.pellet(3,3));
        assertFalse(testMap.pellet(3,4));
        assertFalse(testMap.pellet(4,0));
        assertFalse(testMap.pellet(4,1));
        assertFalse(testMap.pellet(4,2));
        assertFalse(testMap.pellet(4,3));
        assertFalse(testMap.pellet(4,4));
    }

    @Test
    void intersectionPellet() {
        assertTrue(testMap.intersectionPellet(1,1));
        assertTrue(testMap.intersectionPellet(1,3));

        assertFalse(testMap.intersectionPellet(0,0));
        assertFalse(testMap.intersectionPellet(0,1));
        assertFalse(testMap.intersectionPellet(0,2));
        assertFalse(testMap.intersectionPellet(0,3));
        assertFalse(testMap.intersectionPellet(0,4));
        assertFalse(testMap.intersectionPellet(1,0));
        assertFalse(testMap.intersectionPellet(1,2));
        assertFalse(testMap.intersectionPellet(1,4));
        assertFalse(testMap.intersectionPellet(2,0));
        assertFalse(testMap.intersectionPellet(2,1));
        assertFalse(testMap.intersectionPellet(2,2));
        assertFalse(testMap.intersectionPellet(2,3));
        assertFalse(testMap.intersectionPellet(2,4));
        assertFalse(testMap.intersectionPellet(3,0));
        assertFalse(testMap.intersectionPellet(3,1));
        assertFalse(testMap.intersectionPellet(3,2));
        assertFalse(testMap.intersectionPellet(3,3));
        assertFalse(testMap.intersectionPellet(3,4));
        assertFalse(testMap.intersectionPellet(4,0));
        assertFalse(testMap.intersectionPellet(4,1));
        assertFalse(testMap.intersectionPellet(4,2));
        assertFalse(testMap.intersectionPellet(4,3));
        assertFalse(testMap.intersectionPellet(4,4));
    }

    @Test
    void powerPellet() {
        assertTrue(testMap.powerPellet(3,2));

        assertFalse(testMap.powerPellet(0,0));
        assertFalse(testMap.powerPellet(0,1));
        assertFalse(testMap.powerPellet(0,2));
        assertFalse(testMap.powerPellet(0,3));
        assertFalse(testMap.powerPellet(0,4));
        assertFalse(testMap.powerPellet(1,0));
        assertFalse(testMap.powerPellet(1,1));
        assertFalse(testMap.powerPellet(1,2));
        assertFalse(testMap.powerPellet(1,4));
        assertFalse(testMap.powerPellet(2,0));
        assertFalse(testMap.powerPellet(2,1));
        assertFalse(testMap.powerPellet(2,2));
        assertFalse(testMap.powerPellet(2,3));
        assertFalse(testMap.powerPellet(2,4));
        assertFalse(testMap.powerPellet(3,0));
        assertFalse(testMap.powerPellet(3,1));
        assertFalse(testMap.powerPellet(3,3));
        assertFalse(testMap.powerPellet(3,4));
        assertFalse(testMap.powerPellet(4,0));
        assertFalse(testMap.powerPellet(4,1));
        assertFalse(testMap.powerPellet(4,2));
        assertFalse(testMap.powerPellet(4,3));
        assertFalse(testMap.powerPellet(4,4));
    }

    @Test
    void intersectionPowerPellet() {
        assertTrue(testMap.intersectionPowerPellet(3,3));

        assertFalse(testMap.intersectionPowerPellet(0,0));
        assertFalse(testMap.intersectionPowerPellet(0,1));
        assertFalse(testMap.intersectionPowerPellet(0,2));
        assertFalse(testMap.intersectionPowerPellet(0,3));
        assertFalse(testMap.intersectionPowerPellet(0,4));
        assertFalse(testMap.intersectionPowerPellet(1,0));
        assertFalse(testMap.intersectionPowerPellet(1,1));
        assertFalse(testMap.intersectionPowerPellet(1,2));
        assertFalse(testMap.intersectionPowerPellet(1,4));
        assertFalse(testMap.intersectionPowerPellet(2,0));
        assertFalse(testMap.intersectionPowerPellet(2,1));
        assertFalse(testMap.intersectionPowerPellet(2,2));
        assertFalse(testMap.intersectionPowerPellet(2,3));
        assertFalse(testMap.intersectionPowerPellet(2,4));
        assertFalse(testMap.intersectionPowerPellet(3,0));
        assertFalse(testMap.intersectionPowerPellet(3,1));
        assertFalse(testMap.intersectionPowerPellet(3,2));
        assertFalse(testMap.intersectionPowerPellet(3,4));
        assertFalse(testMap.intersectionPowerPellet(4,0));
        assertFalse(testMap.intersectionPowerPellet(4,1));
        assertFalse(testMap.intersectionPowerPellet(4,2));
        assertFalse(testMap.intersectionPowerPellet(4,3));
        assertFalse(testMap.intersectionPowerPellet(4,4));

    }

    @Test
    void testSetTile() {
        //when
        testMap.setTile(1 , 2 , "ES");
        String[][] actual = testMap.getMapArray();
        String[][] expected = {
                {"TL", "HW", "HW", "HW", "TR"},
                {"VW", "II", "ES", "II", "VW"},
                {"T1", "ES", "DD", "ES", "T2"},
                {"VW", "IE", "PP", "IP", "VW"},
                {"BL", "HW", "HW", "HW", "BR"}
        };
        //then
        assertArrayEquals(actual,expected);
    }
}