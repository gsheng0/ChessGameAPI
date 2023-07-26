package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.TileUtil;

public class Move {
    private int startTile, endTile;
    private Piece piece;
    public Move(Piece piece, int startTile, int endTile){
        this.piece = piece;
        this.startTile = startTile;
        this.endTile = endTile;
    }
    public Piece getPiece() { return piece; }
    public int getStartTile() { return startTile; }
    public int getEndTile() { return endTile; }
    public String toString(){
        return piece.getName() + " from " + TileUtil.getNamedTileFromIndex(startTile) + " to " + TileUtil.getNamedTileFromIndex(endTile);
    }
    public String getSimpleName(){
        String name = piece instanceof Knight ? "N" : piece instanceof Pawn ? "" : piece.getName().substring(0, 1);
        return name + TileUtil.getNamedTileFromIndex(endTile);

    }
}
