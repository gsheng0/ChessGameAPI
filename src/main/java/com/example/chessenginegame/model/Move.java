package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.Piece;

public class Move {
    private int startTile, endTile;
    private Piece piece;
    public Move(Piece piece, int endTile){
        this.piece = piece;
        this.startTile = piece.getTile();
        this.endTile = endTile;
    }
    public Piece getPiece() { return piece; }
    public int getStartTile() { return startTile; }
    public int getEndTile() { return endTile; }
}
