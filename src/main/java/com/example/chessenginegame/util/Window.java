package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

public class Window extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
    //TODO: Add logging for games, translate to PGN with chatgpt, paste into analysis on lichess
    //TODO: Add feature to skip to next set of possible moves by either side eg: skip all boards where pawn a6 was played
    //TODO: Add tile names
    //use countMoves() to calculate how many to skip
    private JFrame frame;
    private int tileNumber;
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
    private List<Integer> highlights;
    private Piece selected;
    private List<Board> boards;
    private int moveNumber = 0;
    private static final HashMap<Character, Image> CHARACTER_IMAGE_HASH_MAP = Resources.getCharacterToImageHashMap();
    private MoveGeneratorService moveGenerator;
    public Window(){
        moveGenerator = new MoveGeneratorServiceImpl();
        highlights = new ArrayList<>();
        frame = new JFrame();
        frame.add(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setSize(WINDOW_WIDTH + HORIZONTAL_SHIFT, WINDOW_HEIGHT + VERTICAL_SHIFT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void show(){
        frame.setVisible(true);
    }
    public void setBoards(List<Board> boards){
        this.boards = boards;
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        List<Integer> tilesToHighlight = new ArrayList<>();
        if(selected != null) {
            tilesToHighlight = new ArrayList<>(
                moveGenerator.generateLegalMoves(boards.get(moveNumber), selected.getColor()).stream().
                filter(move -> move.getPiece().getId() == selected.getId()).
                map(Move::getEndTile).
                toList());
            tilesToHighlight.add(tileNumber);
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
                if(tileIndex == 7){
                    g.drawString("" + moveNumber, x * UNIT_WIDTH + 60, 20);
                }

            }
        }
        Board board = boards.get(moveNumber);
        for(int tile : board.getBoard().keySet()){
            int y = tile/8;
            int x = tile % 8;
            drawPieceImage(g, board.getBoard().get(tile), x, y);
        }
    }
    public void drawCharacter(Graphics g, String c, int x, int y){
        g.setFont(SQUARE_FONT);
        g.drawString(c, x * UNIT_WIDTH, y * UNIT_HEIGHT + 80);
    }
    public void drawPieceImage(Graphics g, Piece piece, int x, int y){
        g.drawImage(CHARACTER_IMAGE_HASH_MAP.get(piece.toChar()), x * UNIT_WIDTH, y * UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT, this);
    }
    public void drawPiece(Graphics g, Piece piece, int x, int y){
        if (piece.getColor().equals(Constants.BLACK)) {
            g.setColor(Color.BLACK);
        } else if (piece.getColor().equals(Constants.WHITE)) {
            g.setColor(Color.WHITE);
        }

        if(piece instanceof King){
            drawCharacter(g, "K" + piece.getId(), x, y);
        } else if(piece instanceof Queen){
            drawCharacter(g, "Q" + piece.getId(), x, y);
        } else if(piece instanceof Rook){
            drawCharacter(g, "R" + piece.getId(), x, y);
        } else if(piece instanceof Bishop){
            drawCharacter(g, "B" + piece.getId(), x, y);
        } else if(piece instanceof Knight){
            drawCharacter(g, "N" + piece.getId(), x, y);
        } else if(piece instanceof Pawn){
            drawCharacter(g, "P" + piece.getId(), x, y);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int x = point.x - HORIZONTAL_SHIFT;
        int y = point.y - VERTICAL_SHIFT;
        tileNumber = x/UNIT_WIDTH + 8 * (y/UNIT_HEIGHT);
        if(e.getButton() == MouseEvent.BUTTON1){
            selected = boards.get(moveNumber).getPieceAt(tileNumber).orElse(null);
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
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moveNumber = Math.max(0, moveNumber - 1);
            repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moveNumber = Math.min(moveNumber + 1, boards.size() - 1);
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
