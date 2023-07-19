package com.example.chessenginegame.service;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveGeneratorImpl implements MoveGenerator {
    /**
     *
     * @param board The current board state
     * @param color The side to generate moves for
     * @return A list of legal moves
     */
    @Override
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



        return Collections.emptyList();
    }
    public List<Move> generateKnightMoves(Piece piece, Board board){
        return piece.getMoveShifts().stream().map(
                moveShift -> moveShift + piece.getTile()).
                filter(tile -> board.getPieceAt(tile).isEmpty()).
                map(tile -> new Move(piece, tile)).toList();
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

    /**
     * This method finds a list of pinned pieces by first finding the tile of the king, and looking in the
     * 8 directions, checking for bishops on the diagonals, rooks on the files and ranks, and queens on
     * both, returning only those with one piece in between them and the king
     * @param board The current board state
     * @param color The color the check for
     * @return A list of pinned pieces of the provided color
     */
    public List<Piece> getPinnedPieces(Board board, String color){
        King king = board.getKing(color).orElseThrow(() -> new RuntimeException("No king found for side: " + color));
        return Collections.emptyList();
    }
}
