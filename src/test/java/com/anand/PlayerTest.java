package com.anand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PlayerTest {

    private ImageIcon rightPacman = new ImageIcon("src/main/resources/right.gif");
    private Player player;
    private Map map;

    @BeforeEach
    void createPlayer(){
        map = mock(Map.class);
        player = new Player(14,24,map,"player");
    }



    @Test
    void setDesiredDx() {
        //when
        player.setDesiredDx(1);
        int expected = 1;
        int notExpected = 0;
        int actual = player.getDesiredDx();
        //then
        assertEquals(expected, actual);
        assertNotEquals(notExpected, actual);
    }

    @Test
    void setDesiredDy() {
        //when
        player.setDesiredDy(1);
        int expected = 1;
        int notExpected = 0;
        int actual = player.getDesiredDy();
        //then
        assertEquals(expected, actual);
        assertNotEquals(notExpected, actual);
    }

    @Test
    void getScore() {
        //given
        player.setScore(10);
        //when
        int actual = player.getScore();
        int expected = 10;
        //then
        assertEquals(expected,actual);
    }

    @Test
    void scorePointsOnPellet() {
        //given
        player.setScore(0);
        given(map.pellet(24,14)).willReturn(true);
        //when
        player.scorePoints();
        int expected = 10;
        int actual = player.getScore();
        //then
        assertEquals(expected, actual);
    }
    @Test
    void scorePointsOnIntersectionPellet() {
        //given
        player.setScore(0);
        given(map.intersectionPellet(24,14)).willReturn(true);
        //when
        player.scorePoints();
        int expected = 10;
        int actual = player.getScore();
        //then
        assertEquals(expected, actual);
    }
    @Test
    void doNotScorePointsOnPellet() {
        //given
        player.setScore(0);
        given(map.pellet(24,14)).willReturn(false);
        //when
        player.scorePoints();
        int expected = 0;
        int actual = player.getScore();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void loseLife() {
        //when
        player.loseLife();
        int expected = 2;
        int actual = player.getLives();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void getLives() {
        //when
        int actual = player.getLives();
        int expected = 3;
        //then
        assertEquals(expected,actual);
    }

    @Test
    void teleportOnT1Tile() {
        //given
        given(map.t1(24,14)).willReturn(true);
        //when
        player.teleport();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = Map.WIDTH-4;
        int expectedPx = expectedX*Map.TileSize;
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize;
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);

    }

    @Test
    void teleportOnT2Tile() {
        //given
        given(map.t2(24,14)).willReturn(true);
        //when
        player.teleport();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = 1;
        int expectedPx = expectedX*Map.TileSize;
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize;
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);
    }
    @Test
    void doNotTeleportOnOtherTiles() {
        //given
        given(map.t2(24,14)).willReturn(false);
        //when
        player.teleport();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = 14;
        int expectedPx = expectedX*Map.TileSize;
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize;
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);
    }

    @Test
    void setDirectionXAxisWhenNotBlocked() {
        //when
        player.setDesiredDx(1);
        given(map.blocked( player.getY(),player.getX()+player.getDesiredDx()))
                .willReturn(false);
        //when
        player.setDirection();
        int expectedDx = 1;
        int expectedDy = 0;
        int actualDx = player.getDx();
        int actualDy = player.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }

    @Test
    void setDirectionYAxisWhenNotBlocked() {
        //when
        player.setDesiredDy(1);
        given(map.blocked( player.getY()+player.getDesiredDy(),player.getX()))
                .willReturn(false);
        //when
        player.setDirection();
        int expectedDx = 0;
        int expectedDy = 1;
        int actualDx = player.getDx();
        int actualDy = player.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }
    @Test
    void doNotSetDirectionXAxisWhenNotBlocked() {
        //when
        player.setDesiredDx(-1);
        given(map.blocked( player.getY(),player.getX()+player.getDesiredDx()))
                .willReturn(true);
        //when
        player.setDirection();
        int expectedDx = 0;
        int expectedDy = 0;
        int actualDx = player.getDx();
        int actualDy = player.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }

    @Test
    void doNotSetDirectionYAxisWhenNotBlocked() {
        //when
        player.setDesiredDy(-1);
        given(map.blocked( player.getY()+player.getDesiredDy(),player.getX()))
                .willReturn(true);
        //when
        player.setDirection();
        int expectedDx = 0;
        int expectedDy = 0;
        int actualDx = player.getDx();
        int actualDy = player.getDy();
        //then
        assertEquals(expectedDx,actualDx);
        assertEquals(expectedDy,actualDy);
    }

    @Test
    void moveTeleportTile() {
        //when
        player.setDx(-1);
        given(map.t1(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(true);
        given(map.blocked(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        //when
        player.move();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = Map.WIDTH-4;
        int expectedPx = expectedX*Map.TileSize + player.getDx();
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize;
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);
    }
    @Test
    void moveRegularTile() {
        //when
        player.setDx(-1);
        given(map.t1(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        given(map.blocked(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        //when
        player.move();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = 14;
        int expectedPx = expectedX*Map.TileSize + player.getDx();
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize + player.getDy();
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);
    }
    @Test
    void moveToNextTileWhenLastPixel() {
        //when
        player.setDx(1);
        player.setPx(player.getX()*Map.TileSize + Map.TileSize-1);
        given(map.t1(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        given(map.blocked(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        //when
        player.move();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = 15;
        int expectedPx = expectedX*Map.TileSize;
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize;
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);
    }
    @Test
    void notMoveToNextTileWhenNotLastPixel() {
        //when
        player.setDx(1);
        player.setPx(player.getX()*Map.TileSize + Map.TileSize-5);
        given(map.t1(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        given(map.blocked(
                player.getY()+player.getDy(),
                player.getX()+player.getDx()))
                .willReturn(false);
        //when
        player.move();
        int actualX = player.getX();
        int actualPx = player.getPx();
        int actualY = player.getY();
        int actualPy = player.getPy();
        int expectedX = 14;
        int expectedPx = expectedX*Map.TileSize + Map.TileSize-4;
        int expectedY = 24;
        int expectedPy = 24*Map.TileSize;
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedPx,actualPx);
        assertEquals(expectedY,actualY);
        assertEquals(expectedPy,actualPy);
    }

    @Test
    void resetPos() {
        //when
        player.resetPos(1,1);
        int expectedX = 1;
        int expectedY = 1;
        int actualX = player.getX();
        int actualY = player.getY();
        //then
        assertEquals(expectedX,actualX);
        assertEquals(expectedY,actualY);
    }

}