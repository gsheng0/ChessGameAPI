package com.example.chessenginegame.model.piece;

import java.util.Arrays;
import java.util.List;

public class Rook extends Piece{
    public Rook(String color) {
        super(color);
    }
    @Override
    public List<Integer> getMoveShifts() {
        return Rook.moveShifts();
    }
    public static List<Integer> moveShifts() { return Arrays.asList(-1, 1, 8, -8);}
}
