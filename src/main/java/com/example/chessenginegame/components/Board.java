package com.example.chessenginegame.components;

import java.util.HashMap;
import java.util.Optional;

public class Board {
    private HashMap<Integer, Piece> board;
    public Board(){}
    public Board(HashMap<Integer, Piece> board){
        this.board = board;
    }
    public Optional<Board> apply(Move move){
        //TODO: Move logic into a validate move method
        if(!board.containsKey(move.getStart())){
            return Optional.empty();
        }
        else if(board.get(move.getStart()).getId() == move.getPiece().getId()){
            return Optional.empty();
        }
        return Optional.empty();
    }


}
