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
    private Image img;
    private Map map;
    private String name;
    private int dx, dy;
    private int px, py;

    private int oldX, oldY;

    private ArrayList<Directions> allowedMoves = new ArrayList();

    //constructor
    public Ghost(int x, int y, ImageIcon image, Map map, String name) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.img = image.getImage().getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);
        this.map = map;
        this.name = name;

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
            //System.out.println("first true");
            if (this.map.intersection(y, x)){
                if (!this.map.blocked(y, x+1) && this.dx!=-1)  this.setAllowedMoves(Directions.RIGHT);
                if (!this.map.blocked(y, x-1) && this.dx!=1)  this.setAllowedMoves(Directions.LEFT);
                if (!this.map.blocked(y-1, x) && this.dy!=1)  this.setAllowedMoves(Directions.UP);
                if (!this.map.blocked(y+1, x) && this.dy!=-1)  this.setAllowedMoves(Directions.DOWN);
                //System.out.println(getAllowedMoves());
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
    public ArrayList<Directions> getAllowedMoves() {
        return allowedMoves;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }

/*    public int getX() {
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
    }*/


    public void teleport(){
        if (map.t1(this.y, this.x)) {
            this.x = map.WIDTH - 4;
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
}