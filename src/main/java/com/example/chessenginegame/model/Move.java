package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.TileUtil;

import java.util.ArrayList;
import java.util.List;

public class Move implements Comparable<Move>{
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
        return piece.getColor() + " " + piece.getName() + " "
                + TileUtil.getNamedTileFromIndex(startTile)
                + TileUtil.getNamedTileFromIndex(endTile);
    }
    public String getSimpleName(){
        String name = piece instanceof Knight ? "N" : piece instanceof Pawn ? "" : piece.getName().substring(0, 1);
        return name + TileUtil.getNamedTileFromIndex(endTile);
    }
    public String getUCINotation(){
        return TileUtil.getNamedTileFromIndex(startTile) + TileUtil.getNamedTileFromIndex(endTile);
    }

    /**
     *
     * @param obj another object
     * @return true if the other object is a move object that involves the same piece and tiles
     */
    @Override
    public boolean equals(Object obj) {
        //left like this for readability purposes
        if(obj instanceof Move other){
            return other.piece.getName().equals(this.piece.getName()) &&
               other.piece.getColor().equals(this.piece.getColor()) &&
               other.startTile == this.startTile &&
               other.endTile == this.endTile;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int result = 0;
        result += piece.hashCode() * prime * prime;
        result += endTile * prime + result;
        result += startTile * prime + result;
        return result;
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

    public static List<Move> listOf(Board board, String... uciArr){
        List<Move> moves = new ArrayList<>();
        for(String uci : uciArr){
            moves.add(Move.parseUCIMove(board, uci));
            //board = board.apply(uci);
        }
        return moves;
    }

    @Override
    public int compareTo(Move other) {
        if(startTile != other.startTile){
            return Integer.valueOf(startTile).compareTo(other.startTile);
        } else if(endTile != other.endTile){
            return Integer.valueOf(endTile).compareTo(other.endTile);
        } else return this.getPiece().compareTo(other.getPiece());
    }
}
