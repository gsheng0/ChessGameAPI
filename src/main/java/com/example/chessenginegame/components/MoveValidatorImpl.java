package com.example.chessenginegame.components;

import java.util.Optional;

public class MoveValidatorImpl implements MoveValidator {
    /**
     *
     * @param move The move to be applied to the board
     * @param board The current board state
     * @return true if the move is a legal move for the current board state
     */
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

    /**
     *
     * @param move The move that supplies the starting and end tile to be checked
     * @return true if both the starting and end tiles are within the bounds of the board
     */
    public boolean hasValidTiles(Move move){
        if(move.getStart() < 0 || move.getStart() > 63){
            return false;
        }
        if(move.getEnd() < 0 || move.getEnd() > 63){
            return false;
        }
        return true;
    }

    /**
     *
     * @param move The move that supplies the piece and starting tile to be checked
     * @param board The current board state
     * @return true if the correct piece exists on the correct starting square
     */
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

    /**
     *
     * @param move The move that supplies the end tile to be checked
     * @param board The current board state
     * @return true if the tile does not contain a piece of the same color
     */
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
