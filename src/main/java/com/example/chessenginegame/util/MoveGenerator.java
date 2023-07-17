package com.example.chessenginegame.util;

import com.example.chessenginegame.components.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class MoveGenerator {
    /**
     *
     * @param board The current board state
     * @param color The side to generate moves for
     * @return A list of legal moves
     */
    public List<Move> generateLegalMoves(Board board, String color){
        List<Move> moves = new ArrayList<>();
        List<Piece> pieces = board.getPieces(color);
        for(Piece piece : pieces){
            moves.addAll(generateMovesFor(piece, board));
        }
        return moves;
    }

    /**
     *
     * @param piece The piece to generate moves for
     * @param board The current board state
     * @return A list of all the legal moves for the provided piece in the current board state
     */
    public List<Move> generateMovesFor(Piece piece, Board board){
        if(piece instanceof Pawn){
            return generatePawnMoves(piece, board);
        } else if(piece instanceof Knight){
            return generateKnightMoves(piece, board);
        } else if(piece instanceof Bishop){
            return generateBishopMoves(piece, board);
        } else if(piece instanceof Rook){
            return generateRookMoves(piece, board);
        } else if(piece instanceof Queen){
            return generateQueenMoves(piece, board);
        } else if(piece instanceof King){
            return generateKingMoves(piece, board);
        }
        return Collections.emptyList();
    }
    public List<Move> generatePawnMoves(Piece piece, Board board){
        List<Move> moves = new ArrayList<>();
        List<Integer> tilesToCheck = piece.getMoveShifts().stream().map(
                moveShift -> moveShift + piece.getTile())
                .toList();
        

        return Collections.emptyList();
    }
    public List<Move> generateKnightMoves(Piece piece, Board board){
        return Collections.emptyList();
    }
    public List<Move> generateBishopMoves(Piece piece, Board board){
        return Collections.emptyList();
    }
    public List<Move> generateRookMoves(Piece piece, Board board){
        return Collections.emptyList();
    }
    public List<Move> generateQueenMoves(Piece piece, Board board){
        return Collections.emptyList();
    }
    public List<Move> generateKingMoves(Piece piece, Board board){
        return Collections.emptyList();
    }
}
