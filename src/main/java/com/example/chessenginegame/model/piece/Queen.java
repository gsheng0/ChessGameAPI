package com.example.chessenginegame.model.piece;

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
    public List<Integer> getMoveShifts() {
        return Queen.moveShifts();
    }
    public static List<Integer> moveShifts() { return Arrays.asList(-7, 7, -9, 9, -8, 8, -1, 1);}
}
