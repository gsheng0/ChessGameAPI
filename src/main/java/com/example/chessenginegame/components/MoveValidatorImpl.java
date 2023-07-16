package com.example.chessenginegame.components;

import java.util.Optional;

public class MoveValidatorImpl implements MoveValidator {
    @Override
    public boolean isValid(Move move, Board board)  {
        if(!hasValidTiles(move) || !pieceExistsOnBoard(move, board) || !endTileIsAvailable(move, board)){
            return false;
        }
        Piece piece = board.getPieceAt(move.getStart()).get();
        if(piece instanceof King){

        } else if(piece instanceof Pawn){

        } else if(piece instanceof Knight){

        } else {

        }
        return true;
    }
    public boolean hasValidTiles(Move move){
        if(move.getStart() < 0 || move.getStart() > 63){
            return false;
        }
        if(move.getEnd() < 0 || move.getEnd() > 63){
            return false;
        }
        return true;
    }
    public boolean pieceExistsOnBoard(Move move, Board board){
        Optional<Piece> startTile = board.getPieceAt(move.getStart());
        if(startTile.isEmpty()){
            return false;
        }
        Piece piece = startTile.get();
        if(piece.getId() != move.getPiece().getId()){
            return false;
        }
        return true;
    }
    public boolean endTileIsAvailable(Move move, Board board){
        Optional<Piece> endTile = board.getPieceAt(move.getEnd());
        if(endTile.isEmpty()){
            return true;
        }
        Piece occupant = endTile.get();
        if(occupant.getColor().equals(move.getPiece().getColor())){
            return false;
        }
        return true;
    }
    public boolean tileIsProtectedByOtherColor(Board board, int tile){
        return true;
    }
}
