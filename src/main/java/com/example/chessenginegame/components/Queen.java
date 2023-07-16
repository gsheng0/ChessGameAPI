package com.example.chessenginegame.components;

import java.util.Arrays;
import java.util.List;

public class Queen extends Piece{
    public Queen(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Arrays.asList(-7, 7, -9, 9, -8, 8, -1, 1);
    }
}
