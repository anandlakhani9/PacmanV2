package com.anand.ghost;

import com.anand.Directions;
import com.anand.Map;
import com.anand.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import org.apache.commons.lang3.reflect.FieldUtils;

;import javax.swing.*;

class GhostTest {

    //Ghost testGhost;

    /*@BeforeEach
    void createGhost(){
        testGhost = mock(Ghost.class, CALLS_REAL_METHODS);
    }*/

    //local class used solely to be able to fake instantiate abstract class ghost
    //mockito wasn't ideal here as the values of the class properties are important
    class UnderTest extends Ghost{
        public UnderTest(int x, int y, ImageIcon image, Map map, String name, Player player) {
            super(x, y, image, map, name, player);
        }
    }

    UnderTest underTest;
    Map map;
    Player player;
    private ImageIcon image = new ImageIcon("src/main/resources/ghost.gif");

    @BeforeEach
    void setUpGhost(){
        map = mock(Map.class);
        player = mock(Player.class);
        underTest = new UnderTest(1,1,image, map, "underTest", player);
    }

    @Test
    void setAllowedMovesWhenListIsEmpty() {
      /*  //given
        testGhost.setDirection(Directions.RIGHT);
        //when
        testGhost.setAllowedMoves(Directions.RIGHT);


        List<Directions> actual = testGhost.getAllowedMoves();
        List<Directions> expected = List.of(Directions.RIGHT);
        //then
        assertEquals(expected, actual);*/
        //given
        //when
        underTest.setAllowedMoves(Directions.UP);
        List<Directions> expected = List.of(Directions.UP);
        List<Directions> actual = underTest.getAllowedMoves();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void setAllowedMovesWhenListContainsCurrentMove() {
      /*  //given
        testGhost.setDirection(Directions.RIGHT);
        //when
        testGhost.setAllowedMoves(Directions.RIGHT);


        List<Directions> actual = testGhost.getAllowedMoves();
        List<Directions> expected = List.of(Directions.RIGHT);
        //then
        assertEquals(expected, actual);*/
        //given
        underTest.setAllowedMoves(Directions.UP);
        //when
        underTest.setAllowedMoves(Directions.UP);
        List<Directions> expected = List.of(Directions.UP);
        List<Directions> actual = underTest.getAllowedMoves();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void setAllowedMovesWhenListContainsAnotherMove() {
      /*  //given
        testGhost.setDirection(Directions.RIGHT);
        //when
        testGhost.setAllowedMoves(Directions.RIGHT);


        List<Directions> actual = testGhost.getAllowedMoves();
        List<Directions> expected = List.of(Directions.RIGHT);
        //then
        assertEquals(expected, actual);*/
        //given
        underTest.setAllowedMoves(Directions.UP);
        //when
        underTest.setAllowedMoves(Directions.UP);
        underTest.setAllowedMoves(Directions.DOWN);
        List<Directions> expected = List.of(Directions.UP, Directions.DOWN);
        List<Directions> actual = underTest.getAllowedMoves();
        //then
        assertEquals(expected, actual);
    }

    @Test
    @Disabled
    void populateAllowedMovesWhenCurrentListIsEmptyAndNoDirectionIsBlocked() {
        //given
        //test not working - need argument captors for map.blocked
        given(map.blocked(underTest.getY(), underTest.getX()+1) && underTest.getDx()!=-1)
                .willReturn(false);
        given(map.blocked(underTest.getY(), underTest.getX()-1) && underTest.getDx()!=+1)
                .willReturn(false);
        given(map.blocked(underTest.getY()+1, underTest.getX()) && underTest.getDy()!=1)
                .willReturn(false);
        given(map.blocked(underTest.getY()-1, underTest.getX()) && underTest.getDy()!=-1)
                .willReturn(false);
        //when
        underTest.populateAllowedMoves();
        List<Directions> expected = List.of(
                Directions.RIGHT,
                Directions.LEFT,
                Directions.DOWN,
                Directions.UP
                );
        List<Directions> actual = underTest.getAllowedMoves();
        //then
        assertEquals(expected,actual);
    }

    @Test
    void setDirectionUp() {
        //when
        underTest.setDirection(Directions.UP);
        int expectedDx = 0;
        int expectedDy = -1;
        int actualDx = underTest.getDx();
        int actualDy = underTest.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }
    @Test
    void setDirectionDown() {
        //when
        underTest.setDirection(Directions.DOWN);
        int expectedDx = 0;
        int expectedDy = 1;
        int actualDx = underTest.getDx();
        int actualDy = underTest.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }
    @Test
    void setDirectionRight() {
        //when
        underTest.setDirection(Directions.RIGHT);
        int expectedDx = 1;
        int expectedDy = 0;
        int actualDx = underTest.getDx();
        int actualDy = underTest.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }
    @Test
    void setDirectionLeft() {
        //when
        underTest.setDirection(Directions.LEFT);
        int expectedDx = -1;
        int expectedDy = 0;
        int actualDx = underTest.getDx();
        int actualDy = underTest.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }

    @Test
    void getAllowedMoves() {
        //given
        underTest.setAllowedMoves(Directions.DOWN);
        underTest.setAllowedMoves(Directions.LEFT);
        //when
        List<Directions> actual = underTest.getAllowedMoves();
        List<Directions> expected = List.of(Directions.DOWN, Directions.LEFT);
        //then
        assertEquals(expected,actual);
    }

    @Test
    void setDx() {
        //when
        underTest.setDx(1);
        int actual = underTest.getDx();
        int expected = 1;
        //then
        assertEquals(expected,actual);
    }

    @Test
    void collideWithPlayer() {
        //given

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