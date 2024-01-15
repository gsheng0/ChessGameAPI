package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Constants;

import java.util.List;

public class Queen extends SlidingPiece{
    public Queen(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "Queen";
    }

    @Override
    public int getValue() {
        return 9;
    }
    @Override
    public char toChar() {
        if (getColor().equals(Constants.WHITE)) {
            return 'Q';
        }
        return 'q';
    }
    @Override
    public String toAbv() {
        if (getColor().equals(Constants.WHITE)) {
            return "wQ";
        }
        return "bQ";
    }
    @Override
    public List<Integer> moveShifts(int tile) {
        List<Integer> ms =  straightMoveShifts(tile);
        ms.addAll(diagMoveShifts(tile));
        return ms;
    }
}
