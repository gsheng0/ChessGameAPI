package com.example.chessenginegame.components;

public class Move {
    private int start, end;
    private Piece piece;
    public Move(Piece piece, int end){
        this.piece = piece;
        this.start = piece.getTile();
        this.end = end;
    }
    public Piece getPiece() { return piece; }
    public int getStart() { return start; }
    public int getEnd() { return end; }
}
