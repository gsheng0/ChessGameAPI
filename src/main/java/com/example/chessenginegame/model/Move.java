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
    public String getUCINotation(){
        return TileUtil.getNamedTileFromIndex(startTile) + TileUtil.getNamedTileFromIndex(endTile);
    }
    public static Move parseUCIMove(Board board, String UCI){
        String firstHalf = UCI.substring(0, 2);
        String secondHalf = UCI.substring(2, 4);
        int startTile = TileUtil.getIndexFromNamedTile(firstHalf);
        int endTile = TileUtil.getIndexFromNamedTile(secondHalf);
        Piece piece = board.getPieceAt(startTile).orElseThrow(() -> new IllegalStateException("No piece exists on that start tile"));
        return new Move(piece, startTile, endTile);
    }
    public static Move of(Piece piece, int startTile, int endTile){
        return new Move(piece, startTile, endTile);
    }
}
