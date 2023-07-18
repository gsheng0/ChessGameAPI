package com.example.chessenginegame.util;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {
    private JFrame frame;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    public Window(){
        frame = new JFrame();
        frame.add(this);
        frame.setSize(WINDOW_WIDTH + 1, WINDOW_HEIGHT + 29);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        int UNIT_HEIGHT = WINDOW_HEIGHT/8;
        int UNIT_WIDTH = WINDOW_WIDTH/8;
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if((x + y) % 2 == 0){
                    g.setColor(Color.decode("#f0d9b5"));
                }
                else{
                    g.setColor(Color.decode("#b58862"));
                }

                g.fillRect(x * UNIT_WIDTH, y * UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Lucida", Font.PLAIN, 13));
                g.drawString(x + y * 8 + "", x * UNIT_WIDTH, y * UNIT_HEIGHT + 20);
                g.setFont(new Font("Times New Roman", Font.PLAIN, 60));
                g.drawString("KN", x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
            }
        }
    }
    public static void main(String[] args){
        Window window = new Window();

    }
}
