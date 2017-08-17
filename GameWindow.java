package ru.geekbrains.catch_pepito;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame{


    private static GameWindow gameWindow;
    private static long last_frame_time;
    private static Image pepito;
    private static Image game_over;
    private static Image background;
    private static float pepito_left = 200;
    private static float pepito_top = -100;
    private static float pepito_v = 200;
    private static int score;

    public static void main(String[] args) throws IOException{
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        pepito = ImageIO.read(GameWindow.class.getResourceAsStream("pepito.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200, 100);
        gameWindow.setSize(906, 478);
        gameWindow.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float pepito_right = pepito_left + pepito.getWidth(null);
                float pepito_bottom = pepito_top + pepito.getHeight(null);
                boolean is_pepito = x >= pepito_left && x <= pepito_right && y >= pepito_top && y <= pepito_bottom;
                if(is_pepito){
                    pepito_top = -100;
                    pepito_left = (int) (Math.random() * (gameField.getWidth() - pepito.getWidth(null)));
                    pepito_v = pepito_v + 20;
                    score++;
                    gameWindow.setTitle("Score: " + score);
                }
            }
        });
        gameWindow.add(gameField);
        gameWindow.setVisible(true);

    }

    private static void onRepaint(Graphics g){
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        pepito_top = pepito_top + pepito_v * delta_time;

     g.drawImage(background, 0, 0, null);
     g.drawImage(pepito, (int)pepito_left, (int)pepito_top, null);
     if(pepito_top > gameWindow.getHeight())g.drawImage(game_over, 280, 120, null);
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }

    }
}
