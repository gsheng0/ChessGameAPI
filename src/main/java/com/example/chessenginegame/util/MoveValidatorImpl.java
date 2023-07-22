package com.example.chessenginegame.util;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.King;
import com.example.chessenginegame.model.piece.Knight;
import com.example.chessenginegame.model.piece.Pawn;
import com.example.chessenginegame.model.piece.Piece;

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
        Piece piece = board.getPieceAt(move.getStartTile()).get();
        if(piece instanceof King){
            //need to check for castling moves
            if(tileIsProtectedByOtherColor(board, move.getEndTile())){
                return false;
            }
            return true;

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
        if(move.getStartTile() < 0 || move.getStartTile() > 63){
            return false;
        }
        if(move.getEndTile() < 0 || move.getEndTile() > 63){
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
        Optional<Piece> startTile = board.getPieceAt(move.getStartTile());
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
        Optional<Piece> endTile = board.getPieceAt(move.getEndTile());
        if(endTile.isEmpty()){
            return true;
        }
        Piece occupant = endTile.get();
        if(occupant.getColor().equals(move.getPiece().getColor())){
            return false;
        }
        return true;
    }

    /**
     *
     * @param board The current board state
     * @param tile The tile to be checked
     * @return true if the tile is covered by any piece of the opposing side
     */
    public boolean tileIsProtectedByOtherColor(Board board, int tile){
        return true;
    }

    /**
     *
     * @param move The move supplying the path to be checked
     * @param board The current board state
     * @return true if there exists a piece of any color in between the starting and end tiles
     * @throws IllegalArgumentException if move is not possible by the piece, ignoring all other pieces
     */
    public boolean isPathBlocked(Move move, Board board){
        Piece piece = move.getPiece();
        if(piece instanceof Pawn || piece instanceof Knight || piece instanceof King){
            return false;
        }
        int tileDifference = move.getStartTile() - move.getEndTile();
        int moveShift = -1;
        for(int shift : piece.getMoveShifts()){
            int numTiles = tileDifference / shift;
            if(numTiles > 0 && (tileDifference % shift) == 0){
                moveShift = shift;
                break;
            }
        }
        if(moveShift == -1){
            throw new IllegalArgumentException("move is not possible");
        }
        return true;
    }
}
