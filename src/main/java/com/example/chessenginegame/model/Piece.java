package com.example.chessenginegame.model;

import com.example.chessenginegame.util.Constants;

import java.util.List;

public abstract class Piece{
    private static int counter = 0;
    private int tile;
    private String color;
    private int id;
    private boolean hasMoved = false;
    public Piece(String color, int tile){
        this.color = color;
        this.tile = tile;
        this.id = counter;
        counter++;
    }
    private Piece(){}
    public int getTile() { return tile;}
    public String getColor() { return color; }
    public int getId() { return id; }
    public boolean hasMoved() { return hasMoved; }
    public abstract List<Integer> getMoveShifts();
    public static class PieceBuilder{
        private String color;
        private int tile;
        private Class<? extends Piece> pieceType;
        public PieceBuilder black(){
            this.color = Constants.BLACK;
            return this;
        }
        public PieceBuilder white(){
            this.color = Constants.WHITE;
            return this;
        }
        public PieceBuilder pawn(){
            pieceType = Pawn.class;
            return this;
        }
        public PieceBuilder knight(){
            pieceType = Knight.class;
            return this;
        }
        public PieceBuilder bishop(){
            pieceType = Bishop.class;
            return this;
        }
        public PieceBuilder rook(){
            pieceType = Rook.class;
            return this;
        }
        public PieceBuilder queen(){
            pieceType = Queen.class;
            return this;
        }
        public PieceBuilder king(){
            pieceType = King.class;
            return this;
        }
        public PieceBuilder on(int tile){
            this.tile = tile;
            return this;
        }
        public Piece build(){
            Piece piece;
            if (pieceType.equals(Pawn.class)) {
                piece = new Pawn(color, tile);
            } else if (pieceType.equals(Knight.class)) {
                piece = new Knight(color, tile);
            } else if (pieceType.equals(Bishop.class)) {
                piece = new Bishop(color, tile);
            } else if (pieceType.equals(Rook.class)) {
                piece = new Rook(color, tile);
            } else if (pieceType.equals(Queen.class)) {
                piece = new Queen(color, tile);
            } else if (pieceType.equals(King.class)) {
                piece = new King(color, tile);
            } else {
                throw new IllegalArgumentException("Really not sure how you got this error");
            }
            return piece;
        }

    }
}
