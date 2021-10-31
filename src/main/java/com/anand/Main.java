package com.anand;
import com.anand.ghost.RandomGhost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


public class Main extends Canvas implements Runnable, KeyListener {

    private Map map = new Map(33,30,"src/main/resources/PacmanMap.csv");
    //screen size
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //width and height, constant, accessible by other classes
    public static int screenHeight = screenSize.height;
    private static int screenWidth = screenSize.width;
    private JFrame frame;
    private Thread thread;
    private Boolean running = false;
    ImageIcon right = new ImageIcon("src/main/resources/right.gif");
    RandomGhost rand = new RandomGhost(7,2,right,map,"randomGhost");
    public static void main(String[] args) {
        new Main();

    }

    public Main(){
        //initGame();
        //deal with the game window
        frame = new JFrame("Pacman");
        frame.setResizable(false);
        frame.add(this);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.addKeyListener(this);
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
        g.setColor(Color.BLACK);
        g.fillRect(0, 0 , screenWidth, screenHeight);
        //Translate graphics below here to middle of screen
        g.translate((screenWidth/2) - (Map.WIDTH * Map.TileSize / 2), (screenHeight/2) - (Map.HEIGHT * Map.TileSize / 2));
        //now add the map
        map.paintComponent(g);
        rand.paintComponent(g);

        //end adding graphics before here
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
        final double rate = 3.0 / 1000000000.0 ;

        double delta = 0;

        // vars for fps and updates per sec counting
        int fps = 0;
        int updates = 0;

        long FPStimer = System.currentTimeMillis();

        //Game Loop!
        while (running){
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
                //frame.setTitle("Pacman | FPS: " + fps + " | UPS: " + updates + " | Score: " + player.getScore());
                fps = 0;
                updates = 0;

            }

        }
        stop();
    }

    public void update(){
        rand.populateAllowedMoves();
        rand.setDirection(rand.getMove());
        rand.move();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
