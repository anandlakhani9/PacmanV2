package com.anand;
import com.anand.ghost.RandomGhost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;


public class Main extends Canvas implements Runnable, KeyListener {

    private Map map = new Map(33,30,"src/main/resources/PacmanMap.csv");
    //screen size
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //width and height, constant, accessible by other classes
    public static int screenHeight = screenSize.height;
    private static int screenWidth = screenSize.width;

    private ImageIcon rightPacman = new ImageIcon("src/main/resources/right.gif");
    private Player player = new Player(14,24,map,"player");

    private JFrame frame;
    private Thread thread;
    private Boolean running = false;

    private Font font;



    private ImageIcon randGhostImage = new ImageIcon("src/main/resources/ghost.gif");
    private RandomGhost rand = new RandomGhost(13,15,randGhostImage,map,"randomGhost", player);
    private RandomGhost r2 = new RandomGhost(16,15,randGhostImage,map,"r2", player);
    private RandomGhost r3 = new RandomGhost(15,15,randGhostImage,map,"r3", player);
    private RandomGhost r4 = new RandomGhost(14,15,randGhostImage,map,"r4", player);
    private JLabel endingMessage;
    private boolean gameOver = false;

    public static void main(String[] args)  {
        new Main();

    }

    public Main()  {
        //initGame();
        //deal with the game window
        frame = new JFrame("Pacman");
        frame.setResizable(false);
        frame.add(this);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(this);
        /*String fName = "C:\\Users\\anand\\Documents\\Coding\\PacmanV2\\src\\main\\resources\\slkscr.ttf";
        InputStream is = Main.class.getResourceAsStream(fName);
        font = Font.createFont(Font.TRUETYPE_FONT, is);
        font = font.deriveFont(30);*/

        endingMessage = new JLabel();
        endingMessage.setVisible(false);
        start();
    }

    /*public static void initGame(){
        Map map = new Map(33,30,"src/main/resources/PacmanMap.csv");
        Image right = new ImageIcon("src/main/resources/right.gif").getImage();
        RandomGhost rand = new RandomGhost(15,22,right,map,"randomGhost");
        rand.setDx(-1);
        rand.populateAllowedMoves();
        rand.setDirection(rand.getMove());
        System.out.println(rand);
    }*/

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        //add graphics here
        // this both clears the screen and adds a black border!
        if (!player.isCanEatGhost()) g.setColor(Color.BLACK);
        else g.setColor(Color.GRAY);
        g.fillRect(0, 0 , screenWidth, screenHeight);
        //Translate graphics below here to middle of screen
        g.translate((screenWidth/2) - (Map.WIDTH * Map.TileSize / 2), (screenHeight/2) - (Map.HEIGHT * Map.TileSize / 2));
        //now add the map
        map.paintComponent(g);
        rand.paintComponent(g);
        r2.paintComponent(g);
        r3.paintComponent(g);
        r4.paintComponent(g);
        player.paintComponent(g);
        //end adding graphics before here
        g.dispose();
        bs.show();
    }

    public void renderEnding(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0 , screenWidth, screenHeight);
        g.setColor(Color.YELLOW);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 30));
        if (map.getDotCount() == 0){
            //endingMessage.setText("You win! You scored " + player.getScore() + " and have " + player.getLives() + " remaining!");
            String message = "You win! You scored " + player.getScore() + " and have " + player.getLives() + " remaining!";
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D stringBounds = fm.getStringBounds(message, g);
            g.drawString(message,
                    screenWidth/2 - (int) stringBounds.getWidth()/2,
                    screenHeight/2 - (int) stringBounds.getHeight()/2);
        }
        else {
            //endingMessage.setText("GAME OVER. You scored " + player.getScore());
            //endingMessage.setText("You win! You scored " + player.getScore() + " and have " + player.getLives() + " remaining!");
            String message = "GAME OVER. You scored " + player.getScore();
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D stringBounds = fm.getStringBounds(message, g);
            g.drawString(message,
                    screenWidth/2 - (int) stringBounds.getWidth()/2,
                    screenHeight/2 - (int) stringBounds.getHeight()/2);
        }
        g.dispose();
        bs.show();
    }

    //start a new thread
    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Pacman");
        thread.start();
    }

    //stop the thread if we need to
    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        //get initial system time
        long lastTime = System.nanoTime();
        //this is targeting 100 updates (not frames) per second in nanoseconds
        //final double rate =   250.0 / 1000000000.0 ;
        //as the TileSize depends on the screen size, the game speed should be consistent based on the screensize
        final double rate = 6*Map.TileSize / 1000000000.0 ;
        //System.out.println(rate);

        double delta = 0;

        // vars for fps and updates per sec counting
        int fps = 0;
        int updates = 0;

        long FPStimer = System.currentTimeMillis();

        //Game Loop!
        while (running){
            if (map.getDotCount() == 0){
                //running = false;
                gameOver = true;
                break;
            }
            long now = System.nanoTime();
            //delta will equal approx 1 every 60 seconds
            delta += (now - lastTime) * rate;
            lastTime = now;
            while (delta >= 1){
                //game logic
                update();
                //System.out.println(player.getx() + ", " + player.gety());
                updates++;
                //decrement delta by 1 second
                delta--;
            }

            //runs at unlimited, render graphics
            render();
            fps++;

            if (System.currentTimeMillis() - FPStimer > 1000){
                FPStimer += 1000;
                //System.out.println("FPS: " + fps + ", UPS: " + updates);
                frame.setTitle("Pacman | FPS: " + fps + " | UPS: " + updates + " | Score: " + player.getScore()
                        + " | Lives: " + player.getLives() + "CanEat? " + player.isCanEatGhost());
                fps = 0;
                updates = 0;
            }
        }
        while(gameOver){
            frame.setTitle("GAME OVER");
            //System.out.println("finished");
            renderEnding();
        }
        stop();
    }

    public void update(){
        //System.out.println(player.isCanEatGhost());
        if (player.isCanEatGhost()){
            //System.out.println("hello");
            long timeSoFar = System.nanoTime() - player.getEatGhostTimer();
            //System.out.println(System.nanoTime() - player.getEatGhostTimer());
            if (timeSoFar >= 4000000000L){
                player.setCanEatGhost(false);
            }
            else {
                if(rand.collideWithPlayer()){
                    rand.resetPos(13,15);
                    player.setScore(player.getScore() + 100);
                }
                else if (r2.collideWithPlayer()){
                    r2.resetPos(16,15);
                    player.setScore(player.getScore() + 100);
                }
                else if (r3.collideWithPlayer()){
                    r3.resetPos(15,15);
                    player.setScore(player.getScore() + 100);
                }
                else if (r4.collideWithPlayer()){
                    r4.resetPos(14,15);
                    player.setScore(player.getScore() + 100);
                }
            }

        }
        else if(rand.collideWithPlayer() || r2.collideWithPlayer() || r3.collideWithPlayer() || r4.collideWithPlayer()) {
            player.loseLife();
            player.resetPos(14,24);
            rand.resetPos(13,15);
            r2.resetPos(16,15);
            r3.resetPos(15,15);
            r4.resetPos(14,15);
            if (player.getLives() == 0) {
                this.gameOver = true;
                this.running = false;
            }
        }
        rand.populateAllowedMoves();
        rand.setDirection(rand.getMove());
        rand.move();
        r2.populateAllowedMoves();
        r2.setDirection(r2.getMove());
        r2.move();
        r3.populateAllowedMoves();
        r3.setDirection(r3.getMove());
        r3.move();
        r4.populateAllowedMoves();
        r4.setDirection(r4.getMove());
        r4.move();
        player.scorePoints();
        player.setDirection();
        player.move();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDesiredDx(-1);
            player.setDesiredDy(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDesiredDx(1);
            player.setDesiredDy(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.setDesiredDx(0);
            player.setDesiredDy(-1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDesiredDx(0);
            player.setDesiredDy(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
