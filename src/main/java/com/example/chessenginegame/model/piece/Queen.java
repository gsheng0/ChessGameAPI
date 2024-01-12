package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.util.Constants;

import java.util.Arrays;
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
    public List<Integer> getMoveShifts() {
        return Queen.moveShifts();
    }
    public static List<Integer> moveShifts() { return Arrays.asList(-7, 7, -9, 9, -8, 8, -1, 1);}
}
