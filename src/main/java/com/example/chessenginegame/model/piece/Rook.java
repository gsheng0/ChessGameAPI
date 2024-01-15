package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Constants;

import java.util.List;

public class Rook extends SlidingPiece{
    public Rook(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "Rook";
    }
    @Override
    public int getValue() {
        return 5;
    }
    @Override
    public char toChar() {
        if (getColor().equals(Constants.WHITE)) {
            return 'R';
        }
        return 'r';
    }
    @Override
    public String toAbv() {
        if (getColor().equals(Constants.WHITE)) {
            return "wR";
        }
        return "bR";
    }
    @Override
    public List<Integer> moveShifts(int tile) {
        return straightMoveShifts(tile);
    }
}
