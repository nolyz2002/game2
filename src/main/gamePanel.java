package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class gamePanel  extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    //default player posistion
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public gamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        while(gameThread != null){

            double drawInterval = 1000000000/FPS;
            double nextDrawTime = System.nanoTime() + drawInterval;

            while(gameThread != null) {
                update();

                repaint();
                try{
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime/1000000;

                    if(remainingTime < 0){
                        remainingTime = 0;
                    }

                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);
        g2.dispose();



    }


}
