package com.example.chessenginegame.model;

import java.util.Arrays;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Arrays.asList(-7, 7, -9, 9);
    }
}
