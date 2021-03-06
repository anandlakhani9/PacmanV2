package com.anand.ghost;

import com.anand.Directions;
import com.anand.Map;
import com.anand.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Collections;
import com.google.common.collect.Range;


import static com.anand.Map.TileSize;

public abstract class Ghost {

    // x corresponds to column
    private int x;
    // y corresponds to row
    private int y;
    private ImageIcon image;
    private Image img;
    private Map map;
    private String name;
    private int dx, dy;
    private int px, py;

    private int oldX, oldY;

    private List<Directions> allowedMoves = new ArrayList();

    private Player player;

    //constructor
    public Ghost(int x, int y, ImageIcon image, Map map, String name, Player player) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.img = image.getImage().getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);
        this.map = map;
        this.name = name;
        this.player = player;

        this.px = x * TileSize;
        this.py = y * TileSize;
    }
    //add a move to the list allowed moves if it is not already within the list
    public void setAllowedMoves(Directions d){
        if(!this.allowedMoves.contains(d)) this.allowedMoves.add(d);
    }
    //method to return a list of moves that are not blocked or reversing the direction

    public void populateAllowedMoves(){
        int x = this.x;
        int y = this.y;
        this.allowedMoves.clear();
        /*if (this.map.intersection(x, y)){
            if (!this.map.blocked(x, y+1) && this.dy!=1)  this.setAllowedMoves(Directions.UP);
            if (!this.map.blocked(x, y-1) && this.dy!=-1)  this.setAllowedMoves(Directions.DOWN);
            if (!this.map.blocked(x+1, y) && this.dx!=1)  this.setAllowedMoves(Directions.RIGHT);
            if (!this.map.blocked(x-1, y) && this.dx!=-1)  this.setAllowedMoves(Directions.LEFT);
        }
        else if (this.dx == 1) this.setAllowedMoves(Directions.RIGHT);
        else if (this.dx == -1) this.setAllowedMoves(Directions.LEFT);
        else if (this.dy == 1) this.setAllowedMoves(Directions.UP);
        else if (this.dy == -1) this.setAllowedMoves(Directions.RIGHT);*/

        if (x != this.oldX || y != this.oldY){
            if (this.map.g1(y,x)) this.setAllowedMoves(Directions.RIGHT);
            else if (this.map.g2(y,x)) this.setAllowedMoves(Directions.UP);
            else if (this.map.g3(y,x)) this.setAllowedMoves(Directions.UP);
            else if (this.map.g4(y,x)) this.setAllowedMoves(Directions.LEFT);
            else if (this.map.gU(y,x)) this.setAllowedMoves(Directions.UP);
            else if (this.map.iG(y,x)) {
                if(this.dx != -1) this.setAllowedMoves(Directions.RIGHT);
                if(this.dx != 1) this.setAllowedMoves(Directions.LEFT);
            }

            else if (this.map.intersection(y, x)){
                if (!this.map.blocked(y, x+1) && this.dx!=-1)  this.setAllowedMoves(Directions.RIGHT);
                if (!this.map.blocked(y, x-1) && this.dx!=1)  this.setAllowedMoves(Directions.LEFT);
                if (!this.map.blocked(y-1, x) && this.dy!=1)  this.setAllowedMoves(Directions.UP);
                if (!this.map.blocked(y+1, x) && this.dy!=-1)  this.setAllowedMoves(Directions.DOWN);
            }
            else if (this.dx == 1) this.setAllowedMoves(Directions.RIGHT);
            else if (this.dx == -1) this.setAllowedMoves(Directions.LEFT);
            else if (this.dy == -1) this.setAllowedMoves(Directions.UP);
            else if (this.dy == 1) this.setAllowedMoves(Directions.DOWN);
        }
        else if (this.dx == 1) this.setAllowedMoves(Directions.RIGHT);
        else if (this.dx == -1) this.setAllowedMoves(Directions.LEFT);
        else if (this.dy == -1) this.setAllowedMoves(Directions.UP);
        else if (this.dy == 1) this.setAllowedMoves(Directions.DOWN);
        this.oldX = x;
        this.oldY = y;
    }
    //given a direction, set the dx and dy values for the ghost
    public void setDirection(Directions d){
        if (d==Directions.UP){
            this.dx = 0;
            this.dy = -1;
        }
        else if (d==Directions.DOWN){
            this.dx = 0;
            this.dy=1;
        }
        else if (d==Directions.RIGHT){
            this.dx = 1;
            this.dy=0;
        }
        else if (d==Directions.LEFT){
            this.dx = -1;
            this.dy= 0;
        }
    }
    //more basic getters and setters
    public List<Directions> getAllowedMoves() {
        return allowedMoves;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getDx() {
        return dx;
    }
    public int getDy() {
        return dy;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    //Old collision Method
    /*  OLD Collsion method
  public Boolean collideWithPlayer(){
        //top left of player touches top left of ghost
        if (this.px == player.getPx() && this.py == player.getPy()) return true;
        //top left of player touches top right of ghost
        else if (this.px+TileSize == player.getPx() && this.py == player.getPy()) return true;
        //top right of player touches top left of ghost
        else if (this.px == player.getPx() && this.py+TileSize == player.getPy()) return true;
        //top right of player touches top right of ghost
        else if (this.px+TileSize == player.getPx()+TileSize && this.py == player.getPy()) return true;

        //top left of player touches bottom left of ghost
        else if (this.px == player.getPx() && this.py+TileSize == player.getPy()) return true;
        //bottom left of player touches top left of ghost
        else if (this.px == player.getPx() && this.py == player.getPy()+TileSize) return true;
        return false;

    }*/

    //new collision method
    public Boolean collideWithPlayer() {
        if (Range.open(this.px, this.px + TileSize)
                .isConnected(Range.open(player.getPx(), player.getPx()+TileSize))) {
            if (Range.open(this.py, this.py + TileSize)
                    .isConnected(Range.open(player.getPy(), player.getPy()+TileSize))) {
                return true;
            }
        }
        return false;
    }

    public void teleport(){
        if (map.t1(this.y, this.x)) {
            this.x = Map.WIDTH - 4;
            this.px = this.x * TileSize;
        }
        else if (map.t2(this.y, this.x)) {
            this.x = 1;
            this.px = this.x * TileSize;
        }
    }
    public void move(){
        teleport();
        this.px += dx;
        this.py += dy;
        if (px % TileSize == 0) this.x += dx;
        if (py % TileSize == 0) this.y += dy;
    }

    public void resetPos(int x, int y){
        this.x = x;
        this.y = y;
        this.px = x*TileSize;
        this.py = y*TileSize;
    }
    //paint method
    public void paintComponent(Graphics2D g2d) {
        g2d.drawImage(this.img, px, py, null);
    }

    //to string method for ghost and any subclasses
    @Override
    public String toString() {
        return "Ghost{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", dx=" + dx +
                ", dy=" + dy +
                ", allowedMoves=" + allowedMoves +
                '}';
    }

    public void setOldX(int x){
        this.oldX = x;
    }
    public void setOldY(int y){
        this.oldY = y;
    }
}