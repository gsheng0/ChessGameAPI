package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window extends JPanel implements MouseListener, KeyListener {
    //TODO: Add logging for games, translate to PGN with chatgpt, paste into analysis on lichess
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
    private static MoveGeneratorService moveGenerator;
    private Board board;
    private List<Integer> highlights;
    private Piece selected;
    private List<Board> game;
    private int moveNumber = 0;
    public Window(){
        highlights = new ArrayList<>();
        moveGenerator = new MoveGeneratorServiceImpl();
        ChessGameTester tester = new ChessGameTester();
        game = tester.generateMoves(Board.startingPosition(), 0, 3);
        System.out.println(game.size());
        //board = new Board(map);
        frame = new JFrame();
        frame.add(this);
        frame.addMouseListener(this);
        frame.addKeyListener(this);
        frame.setSize(WINDOW_WIDTH + HORIZONTAL_SHIFT, WINDOW_HEIGHT + VERTICAL_SHIFT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public List<Board> generateRandomGame(int moves){
        List<Board> game = new ArrayList<>();
        Board board = Board.startingPosition();
        game.add(board);
        for(int i = 0; i < moves; i++){
            String color = i % 2 == 0 ? Constants.WHITE : Constants.BLACK;
            List<Move> legalMoves = moveGenerator.generateLegalMoves(board, color);
            if(legalMoves.size() == 0){
                break;
            }
            int rand = (int)(Math.random() * legalMoves.size());
            board = board.apply(legalMoves.get(rand));
            game.add(board);
        }
        return game;
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                int tileIndex = x + (y * 8);
                if((x + y)% 2 == 0){
                    g.setColor(LIGHT_SQUARE_COLOR);
                }
                else{
                    g.setColor(DARK_SQUARE_COLOR);
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
        Board board = game.get(moveNumber);
        for(int tile : board.getBoard().keySet()){
            int y = tile/8;
            int x = tile % 8;
            drawPiece(g, board.getBoard().get(tile), x, y);
        }
    }
    public void drawCharacter(Graphics g, String c, int x, int y){
        g.setFont(SQUARE_FONT);
        g.drawString(c, x * UNIT_WIDTH, y * UNIT_HEIGHT + 80);
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
    public static void main(String[] args){
        Window window = new Window();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        Point point = e.getPoint();
//        int x = point.x - HORIZONTAL_SHIFT;
//        int y = point.y - VERTICAL_SHIFT;
//        int tileNumber = x/UNIT_WIDTH + 8 * (y/UNIT_HEIGHT);
//        if(e.getButton() == MouseEvent.BUTTON1){
//            selected = board.getPieceAt(tileNumber).orElse(null);
//        }
//        else{
//            selected = null;
//            if(highlights.contains(tileNumber)){
//                highlights.remove(Integer.valueOf(tileNumber));
//            } else{
//                highlights.add(tileNumber);
//            }
//        }
        moveNumber++;
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
            moveNumber = Math.min(moveNumber + 1, game.size() - 1);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
