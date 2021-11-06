package com.anand.ghost;

import com.anand.Directions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import org.apache.commons.lang3.reflect.FieldUtils;

;

class GhostTest {

    Ghost testGhost;

    @BeforeEach
    void createGhost(){
        testGhost = mock(Ghost.class, CALLS_REAL_METHODS);
    }

    @Test
    void setAllowedMoves() {
        //given
        testGhost.setDirection(Directions.RIGHT);
        //when
        testGhost.setAllowedMoves(Directions.RIGHT);


        List<Directions> actual = testGhost.getAllowedMoves();
        List<Directions> expected = List.of(Directions.RIGHT);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void populateAllowedMoves() {
    }

    @Test
    void setDirection() {
    }

    @Test
    void getAllowedMoves() {
    }

    @Test
    void setDx() {
    }

    @Test
    void collideWithPlayer() {
    }

    @Test
    void teleport() {
    }

    @Test
    void move() {
    }

    @Test
    void paintComponent() {
    }

    @Test
    void testToString() {
    }
}