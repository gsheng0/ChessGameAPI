package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.piece.Piece;

import java.util.Arrays;
import java.util.List;

public class Bishop extends SlidingPiece {
    public Bishop(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "Bishop";
    }
    @Override
    public int getValue() {
        return 3;
    }
    @Override
    public List<Integer> getMoveShifts() {
        return Bishop.moveShifts();
    }
    public static List<Integer> moveShifts(){return Arrays.asList(-7, 7, -9, 9); }
}
