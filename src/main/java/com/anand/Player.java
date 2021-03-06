package com.anand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.anand.Map.TileSize;

public class Player {
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

    private int desiredDx, desiredDy;
    private int oldX, oldY;

    private int lives;
    private int score;
    private ArrayList<Directions> allowedMoves = new ArrayList();

    private Image right = new ImageIcon("src/main/resources/right.gif").getImage()
            .getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);
    private Image rightScaled = new ImageIcon(right).getImage();
    private Image left = new ImageIcon("src/main/resources/left.gif").getImage()
            .getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);;
    private Image leftScaled = new ImageIcon(left).getImage();
    private Image up = new ImageIcon("src/main/resources/up.gif").getImage()
                .getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);;
    private Image upScaled = new ImageIcon(up).getImage();
    private Image down = new ImageIcon("src/main/resources/down.gif").getImage()
                .getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);
    private Image downScaled = new ImageIcon(down).getImage();
    //nanoseconds
    private long eatGhostTimer;

    private boolean canEatGhost;
    //start at row 24 col 14
    //constructor
    public Player(int x, int y, Map map, String name) {
        this.x = x;
        this.y = y;
        //this.image = image;
        //this.img = image.getImage().getScaledInstance(TileSize,TileSize,Image.SCALE_DEFAULT);
        //initImages();
        this.img = rightScaled;
        this.map = map;
        this.name = name;
        this.lives = 3;
        this.px = x * TileSize;
        this.py = y * TileSize;
        this.canEatGhost = false;
        //this.img = setImg(getRightScaled());
        this.eatGhostTimer = 0L;
    }

    public long getEatGhostTimer() {
        return eatGhostTimer;
    }

    public void setEatGhostTimer(long eatGhostTimer) {
        this.eatGhostTimer = eatGhostTimer;
    }

    public void setDesiredDx(int direction){
        this.desiredDx = direction;
    }
    public void setDesiredDy(int direction){
        this.desiredDy = direction;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getScore() {
        return score;
    }

    public boolean isCanEatGhost() {
        return canEatGhost;
    }

    public void setCanEatGhost(boolean canEatGhost) {
        this.canEatGhost = canEatGhost;
    }

    public void scorePoints(){
        //System.out.println(map.pellet(this.y, this.x));
        if (map.pellet(this.y, this.x)){
            //System.out.println("point");
            map.setTile(this.y, this.x, "ES");
            map.setDotCount(map.getDotCount() - 1);
            score += 10;
        }
        else if (map.intersectionPellet(this.y, this.x)){
            map.setTile(this.y, this.x, "IE");
            score += 10;
            map.setDotCount(map.getDotCount() - 1);
        }
        else if (map.intersectionPowerPellet(this.y, this.x)){
            map.setTile(this.y, this.x, "IE");
            score+=50;
            this.canEatGhost = true;
            this.eatGhostTimer = System.nanoTime();
            map.setDotCount(map.getDotCount() - 1);
        }
        else if (map.powerPellet(this.y, this.x)){
            map.setTile(this.y, this.x, "ES");
            score += 50;
            this.canEatGhost = true;
            this.eatGhostTimer = System.nanoTime();
            map.setDotCount(map.getDotCount() - 1);
        }
    }

    public void loseLife(){
        this.lives --;
    }
    public int getLives(){
        return this.lives;
    }

    public void teleport(){
        if (map.t1(this.y + dy, this.x + dx)) {
            //System.out.println("teleport");
            this.x = map.WIDTH - 4;
            this.px = this.x * TileSize;
        }
        else if (map.t2(this.y + dy, this.x + dx)) {
            //System.out.println("teleport");
            this.x = 1;
            this.px = this.x * TileSize;
        }
    }

    public void setDirection(){
        if (!map.blocked(y+desiredDy, x+desiredDx)){
            if (px % TileSize == 0 && py % TileSize == 0){
                this.dx = this.desiredDx;
                this.dy = this.desiredDy;
                if (this.dx == 1) this.img = rightScaled;
                else if (this.dx == -1) this.img = leftScaled;
                else if (this.dy == 1) this.img = downScaled;
                else if (this.dy == -1) this.img = upScaled;
            }

        }
    }
    public void move(){
        teleport();
        if (!map.blocked(y+ dy, x + dx)){
            this.px += dx;
            this.py += dy;
            if (px % TileSize == 0) this.x += dx;
            if (py % TileSize == 0) this.y += dy;
        }
    }

    public void resetPos(int x, int y){
        this.x = x;
        this.y = y;
        this.px = x*TileSize;
        this.py = y*TileSize;
        this.dx = 0;
        this.dy = 0;
    }

    //paint method
    public void paintComponent(Graphics2D g2d) {
        g2d.drawImage(this.img, px, py, null);
    }

    public int getPx() {
        return this.px;
    }
    public int getPy() {
        return this.py;
    }

    public int getDesiredDx() {
        return this.desiredDx;
    }
    public int getDesiredDy() {
        return this.desiredDy;
    }

    public void setScore(int i) {
        this.score = i;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
