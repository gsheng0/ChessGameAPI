package com.example.chessenginegame.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rook extends Piece{
    public Rook(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Arrays.asList(-1, 1, 8, -8);
    }
}
