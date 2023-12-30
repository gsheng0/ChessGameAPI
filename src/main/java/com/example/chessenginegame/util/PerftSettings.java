package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;

public class PerftSettings {
    private Board boardState;
    private int depth;
    public PerftSettings(){
        boardState = Board.startingPosition();
        depth = 1;
    }
    public void setDepth(int depth){
        this.depth = depth;
    }
    public void resetStartingPosition(){
        boardState = Board.startingPosition();
    }
    public void applyMoveToBoardState(String move){
        boardState.apply(move);
    }
    public void setBoardState(String FEN){
        boardState = Board.createFromFEN(FEN);
    }
    public Board getBoardState(){
        return boardState;
    }
    public int getDepth(){
        return depth;
    }

}
