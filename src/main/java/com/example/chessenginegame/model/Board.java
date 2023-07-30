package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.King;
import com.example.chessenginegame.model.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Board {
    private HashMap<Integer, Piece> board;
    private Move previousMove;
    public Board(){}
    public Board(HashMap<Integer, Piece> board){
        this.board = board;
    }
    public Board(HashMap<Integer, Piece> board, Move previousMove){
        this.board = board;
        this.previousMove = previousMove;
    }

    /**
     *
     * @param move The move to be applied to the board
     * @return a copy of the board, with the move appplied
     */
    public Board apply(Move move){
        if(!board.containsKey(move.getStartTile())){
            throw new IllegalArgumentException("Move not applicable to board: missing piece on start tile");
        }
        HashMap<Integer, Piece> newBoard = copyBoard(board);
        Piece piece = newBoard.remove(move.getStartTile());
        newBoard.put(move.getEndTile(), piece);
        return new Board(newBoard, move);
    }
    public Board apply(List<Move> moves){
        Board current = this;
        for(Move move : moves){
            current = current.apply(move);
        }
        //TODO:
        return current;
    }
    public Board apply(String uci){
        return apply(Move.parseUCIMove(this, uci));
    }
    public Optional<Piece> getPieceAt(int tile){
        if(!board.containsKey(tile)){
            return Optional.empty();
        }
        return Optional.of(board.get(tile));
    }
    public List<Integer> getPieceTiles(String color){
        return board.keySet().stream().filter(tile -> board.get(tile).getColor().equals(color)).toList();
    }
    public Optional<Integer> getTileOfKing(String color){
        for(int tile : board.keySet()){
            Piece piece = board.get(tile);
            if(!(piece instanceof King)){
                continue;
            }
            if(piece.getColor().equals(color)){
                return Optional.of(tile);
            }
        }
        return Optional.empty();
    }
    public static Board startingPosition() {
        return Board.createFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }
    public Optional<Move> getPreviousMove(){
        return Optional.ofNullable(previousMove);
    }
    public HashMap<Integer, Piece> getBoard(){
        return board;
    }
    public static Board createFromFEN(String FEN){
        HashMap<Integer, Piece> board = new HashMap<>();
        int index = 0;
        for(int i = 0; i < FEN.length(); i++){
            if(index > 63){
                break;
            }
            char current = FEN.charAt(i);
            if(current == '/'){
                continue;
            }
            if(current > '0' && current < '9'){
                int num = current - '0';
                index += num;
            }
            else{
                board.put(index, Piece.buildFromCharacter(current));
                index++;
            }
        }
        return new Board(board);
    }
    private static HashMap<Integer, Piece> copyBoard(HashMap<Integer, Piece> board){
        HashMap<Integer, Piece> copy = new HashMap<>();
        for(int tile : board.keySet()){
            copy.put(tile, board.get(tile));
        }
        return copy;
    }

}
