package com.anand;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Map {

    //TODO is final appropriate - may need to change
    //private final String[][] mapArray = new String[33][30];
    private String[][] mapArray;
    private int rows;
    private int columns;
    private String src;

    /** The width in grid cells of our map */
    public static final int WIDTH = 32;
    /** The height in grid cells of our map */
    public static final int HEIGHT = 32;
    public static final int TileSize = Main.screenHeight/HEIGHT - 3;

    //images
    private Image tlImage;
    private Image tlImageScaled;
    private Image blImage;
    private Image blImageScaled;
    private Image trImage;
    private Image trImageScaled;
    private Image brImage;
    private Image brImageScaled;
    private Image vwImage;
    private Image vwImageScaled;
    private Image hwImage;
    private Image hwImageScaled;
    private int dotCount = 0;

    //constructor for the map, reads in map as an array from the csv file containing the map layout
    public Map(int rows, int columns, String src) {
        //old code - now in initMap() - too scared to delete it yet!
        /*String pathToMap = "src/main/resources/PacmanMap.csv";
        String line;
        String[] row;
        try {
            BufferedReader br = new BufferedReader(new FileReader(pathToMap));
            int j = 0;
            while ((line = br.readLine()) != null) {
                row = line.split(",");
                System.arraycopy(row, 0, mapArray[j], 0, 30);
                System.out.println(Arrays.toString(mapArray[j]));
                if (j == 32) j = 0;
                else j++;
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }*/
        //new code
        this.rows = rows;
        this.columns = columns;
        this.src = src;
        this.mapArray = initMap(this.rows, this.columns, this.src);
        initImages();
        countDots();
    }


    public void initImages(){
        tlImage = new ImageIcon("src/main/resources/MapImages/top_left.png").getImage()
                .getScaledInstance(TileSize , TileSize , Image.SCALE_SMOOTH);
        tlImageScaled = new ImageIcon(tlImage).getImage();

        blImage = new ImageIcon("src/main/resources/MapImages/bottom_left.png").getImage()
                .getScaledInstance(TileSize , TileSize , Image.SCALE_SMOOTH);
        blImageScaled = new ImageIcon(blImage).getImage();

        trImage = new ImageIcon("src/main/resources/MapImages/top_right.png").getImage()
                .getScaledInstance(TileSize , TileSize , Image.SCALE_SMOOTH);
        trImageScaled = new ImageIcon(trImage).getImage();

        brImage = new ImageIcon("src/main/resources/MapImages/bottom_right.png").getImage()
                .getScaledInstance(TileSize , TileSize , Image.SCALE_SMOOTH);
        brImageScaled = new ImageIcon(brImage).getImage();

        vwImage = new ImageIcon("src/main/resources/MapImages/vertical_line.png").getImage()
                .getScaledInstance(TileSize , TileSize , Image.SCALE_SMOOTH);
        vwImageScaled = new ImageIcon(vwImage).getImage();

        hwImage = new ImageIcon("src/main/resources/MapImages/horizontal_line.png").getImage()
                .getScaledInstance(TileSize , TileSize , Image.SCALE_SMOOTH);
        hwImageScaled = new ImageIcon(hwImage).getImage();
    }

    //initialise the map. Call in constructor to set the map array, as it needs to be done to create the map
    public String[][] initMap(int rows, int columns, String src){
        String[][] mapArray = new String[rows][columns];
        String line;
        String[] row;
        try {
            BufferedReader br = new BufferedReader(new FileReader(src));
            int j = 0;
            while ((line = br.readLine()) != null) {
                row = line.split(",");
                System.arraycopy(row, 0, mapArray[j], 0, columns);
                //System.out.println(Arrays.toString(mapArray[j]));
                if (j == rows-1) j = 0;
                else j++;
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }

        return mapArray;
    }

    public void countDots(){
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                String tile = this.mapArray[i][j];
                if (tile.equals("DD")
                        || tile.equals("II")
                        ||tile.equals("IP")
                        || tile.equals("PP")){
                    dotCount++;

                }
            }
        }
    }

    //returns whether a tile is blocked
    public boolean blocked(int row, int column){
        //return any of the different wall types
        return (
                mapArray[row][column].equals("TL")
                        || mapArray[row][column].equals("TR")
                        || mapArray[row][column].equals("HW")
                        || mapArray[row][column].equals("VW")
                        || mapArray[row][column].equals("BR")
                        || mapArray[row][column].equals("BL")
        );
    }
    //returns if a tile is an intersection tile
    public boolean intersection(int row, int column){
        //return any of the different wall types
        return (
                mapArray[row][column].equals("II")
                        || mapArray[row][column].equals("IP")
                        || mapArray[row][column].equals("IE")
        );
    }

    public boolean t1(int row, int column){
        //return any of the different wall types
        return (
                mapArray[row][column].equals("T1")
        );
    }
    public boolean t2(int row, int column){
        //return any of the different wall types
        return (
                mapArray[row][column].equals("T2")
        );
    }

    public boolean pellet(int row, int column){
        return mapArray[row][column].equals( "DD") ;
    }
    public boolean powerPellet(int row, int column){
        return mapArray[row][column].equals( "PP") ;
    }
    public boolean intersectionPellet(int row, int column){
        return mapArray[row][column].equals( "II") ;
    }
    public boolean intersectionPowerPellet(int row, int column){
        return mapArray[row][column].equals( "IP") ;
    }

    public boolean g1(int row, int column){
        return mapArray[row][column].equals("G1");
    }
    public boolean g2(int row, int column){
        return mapArray[row][column].equals("G2");
    }
    public boolean g3(int row, int column){
        return mapArray[row][column].equals("G3");
    }
    public boolean g4(int row, int column){
        return mapArray[row][column].equals("G4");
    }
    public boolean gU(int row, int column){
        return mapArray[row][column].equals("GU");
    }
    public boolean iG(int row, int column) {
        return mapArray[row][column].equals("IG");
    }
    //getters and setters
    /*public String getTile(int row, int column){
        return mapArray[row][column];
    }*/

    public void setTile(int row, int column, String value){
        mapArray[row][column] = value;

    }
    public String[][] getMapArray(){
        return this.mapArray;
    }

    public int getDotCount() {
        return dotCount;
    }

    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
    }

    //this method has not been unit tested as it has clear visual feedback on the screen when it works
    public void paintComponent(Graphics2D g2d){

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                String tile = this.mapArray[i][j];
                if (tile.equals("TL")){
                    g2d.drawImage(tlImageScaled, j* TileSize, i*TileSize, null);
                }
                else if (tile.equals("BL")){
                    g2d.drawImage(blImageScaled, j* TileSize, i*TileSize, null);
                }
                else if (tile.equals("TR")){
                    g2d.drawImage(trImageScaled, j* TileSize, i*TileSize, null);
                }
                else if (tile.equals("BR")){
                    g2d.drawImage(brImageScaled, j* TileSize, i*TileSize, null);
                }
                else if (tile.equals("VW")){
                    g2d.drawImage(vwImageScaled, j* TileSize, i*TileSize, null);
                }
                else if (tile.equals("HW")){
                    g2d.drawImage(hwImageScaled, j* TileSize, i*TileSize, null);
                }
                else if (tile.equals("DD")
                || tile.equals("II")){
                    g2d.setColor(Color.white);
                    int rDot = TileSize/4;
                    //position the dots to the middle of the maze paths
                    int dot_pos_x = j * TileSize + (TileSize - rDot)/2;
                    int dot_pos_y = i * TileSize + (TileSize - rDot)/2;
                    g2d.drawOval(dot_pos_x, dot_pos_y, rDot, rDot);
                    g2d.fillOval(dot_pos_x, dot_pos_y, rDot, rDot);
                }
                else if (tile.equals("IP")
                        || tile.equals("PP")){
                    g2d.setColor(Color.white);
                    int rDot = TileSize/2;
                    //position the dots to the middle of the maze paths
                    int dot_pos_x = j * TileSize + (TileSize - rDot)/2;
                    int dot_pos_y = i * TileSize + (TileSize - rDot)/2;
                    g2d.drawOval(dot_pos_x, dot_pos_y, rDot, rDot);
                    g2d.fillOval(dot_pos_x, dot_pos_y, rDot, rDot);
                }
                /*if (tile.equals("IE") || tile.equals("II") || tile.equals("IP") ){
                    g2d.setColor(Color.red);
                    g2d.drawRect(j*TileSize, i*TileSize, TileSize, TileSize);
                }*/

            }

        }
    }


}
