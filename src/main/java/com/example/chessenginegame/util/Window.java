package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.piece.*;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {
    private JFrame frame;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color LIGHT_SQUARE_COLOR = Color.decode("#f0d9b5");
    private static final Color DARK_SQUARE_COLOR = Color.decode("#b58862");
    private static final Font SQUARE_NUMBER_FONT = new Font("Lucida", Font.PLAIN, 13);
    private static final Font PIECE_FONT = new Font("Times New Roman", Font.PLAIN, 60);
    private static final int UNIT_HEIGHT = WINDOW_HEIGHT/8;
    private static final int UNIT_WIDTH = WINDOW_WIDTH/8;
    private Board board;
    public Window(){
        frame = new JFrame();
        frame.add(this);
        frame.setSize(WINDOW_WIDTH + 1, WINDOW_HEIGHT + 29);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board = Board.createFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                int tileIndex = x + y;
                if(tileIndex% 2 == 0){
                    g.setColor(LIGHT_SQUARE_COLOR);
                }
                else{
                    g.setColor(DARK_SQUARE_COLOR);
                }
                g.fillRect(x * UNIT_WIDTH, y * UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);

                g.setColor(Color.BLACK);
                g.setFont(SQUARE_NUMBER_FONT);
                g.drawString(x + y * 8 + "", x * UNIT_WIDTH, y * UNIT_HEIGHT + 20);
            }
        }
        for(int tile : board.getBoard().keySet()){
            int y = tile/8;
            int x = tile % 8;
            drawPiece(g, board.getPieceAt(tile).get(), x, y);
        }
    }
    public void drawPiece(Graphics g, Piece piece, int x, int y){
        g.setFont(PIECE_FONT);
        if (piece.getColor().equals(Constants.BLACK)) {
            g.setColor(Color.BLACK);
        } else if (piece.getColor().equals(Constants.WHITE)) {
            g.setColor(Color.WHITE);
        }
        if(piece instanceof King){
            g.drawString("K", x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
        } else if(piece instanceof Queen){
            g.drawString("Q",  x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
        } else if(piece instanceof Rook){
            g.drawString("R",  x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
        } else if(piece instanceof Bishop){
            g.drawString("B",  x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
        } else if(piece instanceof Knight){
            g.drawString("N",  x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
        } else if(piece instanceof Pawn){
            g.drawString("P",  x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
        }
    }
    public static void main(String[] args){
        Window window = new Window();

    }
}
