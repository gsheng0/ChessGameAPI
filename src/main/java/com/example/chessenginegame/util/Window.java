package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.service.MoveGenerator;
import com.example.chessenginegame.service.MoveGeneratorImpl;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window extends JPanel implements MouseListener {
    private JFrame frame;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final Color LIGHT_SQUARE_COLOR = Color.decode("#f0d9b5");
    private static final Color DARK_SQUARE_COLOR = Color.decode("#b58862");
    private static final Font SQUARE_NUMBER_FONT = new Font("Lucida", Font.PLAIN, 13);
    private static final Font SQUARE_FONT = new Font("Times New Roman", Font.PLAIN, 60);
    private static final int UNIT_HEIGHT = WINDOW_HEIGHT/8;
    private static final int UNIT_WIDTH = WINDOW_WIDTH/8;
    private static final int HORIZONTAL_SHIFT = 1;
    private static final int VERTICAL_SHIFT = 29;
    private static MoveGenerator moveGenerator;
    private Board board;
    private List<Integer> highlights;
    private Piece selected;
    public Window(){
        highlights = new ArrayList<>();
        moveGenerator = new MoveGeneratorImpl();
        HashMap<Integer, Piece> map = new HashMap<>();
        map.put(36, new Rook(Constants.WHITE, 36));
        board = new Board(map);
        frame = new JFrame();
        frame.add(this);
        frame.addMouseListener(this);
        frame.setSize(WINDOW_WIDTH + HORIZONTAL_SHIFT, WINDOW_HEIGHT + VERTICAL_SHIFT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        List<Move> moveList = moveGenerator.generateLegalMoves(board, Constants.WHITE);
        moveList.addAll(moveGenerator.generateLegalMoves(board, Constants.BLACK));
        List<Integer> tilesToHighlight = new ArrayList<>();
        if(selected != null){
            for(Move move : moveList){
                if(move.getPiece().getId() == selected.getId()){
                    tilesToHighlight.add(move.getEnd());
                }
            }
            System.out.println("Tiles to Highlight: "+ tilesToHighlight);
        }
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                int tileIndex = x + (y * 8);
                if((x + y)% 2 == 0){
                    g.setColor(LIGHT_SQUARE_COLOR);
                }
                else{
                    g.setColor(DARK_SQUARE_COLOR);
                }

                if(tilesToHighlight.contains(tileIndex)){
                    g.setColor(Color.CYAN);
                }
                g.fillRect(x * UNIT_WIDTH, y * UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);

                if(highlights.contains(tileIndex)){
                    g.setColor(Color.GREEN);
                    g.drawOval(x * UNIT_WIDTH + 5, y * UNIT_HEIGHT + 5, UNIT_WIDTH - 10, UNIT_HEIGHT - 10);
                }

                g.setColor(Color.BLACK);
                g.setFont(SQUARE_NUMBER_FONT);
                g.drawString(tileIndex + "", x * UNIT_WIDTH, y * UNIT_HEIGHT + 20);

            }
        }
        for(int tile : board.getBoard().keySet()){
            int y = tile/8;
            int x = tile % 8;
            drawPiece(g, board.getPieceAt(tile).get(), x, y);
        }
    }
    public void drawCharacter(Graphics g, String c, int x, int y){
        g.setFont(SQUARE_FONT);
        g.drawString("" + c.charAt(0), x * UNIT_WIDTH + 10, y * UNIT_HEIGHT + 80);
    }
    public void drawPiece(Graphics g, Piece piece, int x, int y){
        if (piece.getColor().equals(Constants.BLACK)) {
            g.setColor(Color.BLACK);
        } else if (piece.getColor().equals(Constants.WHITE)) {
            g.setColor(Color.WHITE);
        }
        if(piece instanceof King){
            drawCharacter(g, "K", x, y);
        } else if(piece instanceof Queen){
            drawCharacter(g, "Q", x, y);
        } else if(piece instanceof Rook){
            drawCharacter(g, "R", x, y);
        } else if(piece instanceof Bishop){
            drawCharacter(g, "B", x, y);
        } else if(piece instanceof Knight){
            drawCharacter(g, "N", x, y);
        } else if(piece instanceof Pawn){
            drawCharacter(g, "P", x, y);
        }
    }
    public static void main(String[] args){
        Window window = new Window();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int x = point.x - HORIZONTAL_SHIFT;
        int y = point.y - VERTICAL_SHIFT;
        int tileNumber = x/UNIT_WIDTH + 8 * (y/UNIT_HEIGHT);
        if(e.getButton() == MouseEvent.BUTTON1){
            selected = board.getPieceAt(tileNumber).orElse(null);
        }
        else{
            selected = null;
            if(highlights.contains(tileNumber)){
                highlights.remove(Integer.valueOf(tileNumber));
            } else{
                highlights.add(tileNumber);
            }
        }
        repaint();


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
