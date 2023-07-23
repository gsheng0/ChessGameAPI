package com.example.chessenginegame.model.piece;

import java.util.Arrays;
import java.util.List;

public class Queen extends Piece{
    public Queen(String color) {
        super(color);
    }
    @Override
    public List<Integer> getMoveShifts() {
        return Queen.moveShifts();
    }
    public static List<Integer> moveShifts() { return Arrays.asList(-7, 7, -9, 9, -8, 8, -1, 1);}
}
