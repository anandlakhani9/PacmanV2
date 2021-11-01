package com.anand.ghost;

import com.anand.Directions;
import com.anand.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static com.anand.Map.TileSize;

public abstract class Ghost {

    // x corresponds to column
    private int x;
    // y corresponds to row
    private int y;
    private ImageIcon image;
    Image img;
    private Map map;
    private String name;
    private int dx, dy;

    //order up, down, left right
    //true if move possible, therefore if (allowedMoves[0]){} will execute if you can move up
    private ArrayList<Directions> allowedMoves = new ArrayList();

    //constructor
    public Ghost(int x, int y, ImageIcon image, Map map, String name) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.img = image.getImage().getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);
        this.map = map;
        this.name = name;

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

        if (this.map.intersection(y, x)){
            System.out.println("row: " + y + ", column: " + x);
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
    public ArrayList<Directions> getAllowedMoves() {
        return allowedMoves;
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
    public void setDx(int dx) {
        this.dx = dx;
    }
    public int getDy() {
        return dy;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }


    public void teleport(){
        if (map.t1(this.y, this.x)) this.setX(map.WIDTH -3);
        else if (map.t2(this.y, this.x)) this.setX(1);
    }
    public void move(){
        teleport();
        this.x += dx;
        this.y += dy;
    }
    //paint method
    public void paintComponent(Graphics2D g2d) {
        g2d.drawImage(this.img, this.x * TileSize, this.y *TileSize, null);
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
}