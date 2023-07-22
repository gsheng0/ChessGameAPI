package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.King;
import com.example.chessenginegame.model.piece.Piece;

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
    public Optional<Board> apply(Move move){
        if(!board.containsKey(move.getStartTile())){
            return Optional.empty();
        }
        else if(board.get(move.getStartTile()).getId() == move.getPiece().getId()){
            return Optional.empty();
        }
        return Optional.empty();
    }
    public Optional<Piece> getPieceAt(int tile){
        if(!board.containsKey(tile)){
            return Optional.empty();
        }
        return Optional.of(board.get(tile));
    }
    public List<Piece> getPieces(String color){
        return board.values().stream().
                filter(piece -> piece.getColor().equals(color)).
                toList();
    }
    public Optional<King> getKing(String color){
        for(Piece piece : board.values()){
            if(!(piece instanceof King)){
                continue;
            }
            if(piece.getColor().equals(color)){
                return Optional.of((King)piece);
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
                board.put(index, Piece.buildFromCharacter(current, index));
                index++;
            }
        }
        return new Board(board);
    }


}
