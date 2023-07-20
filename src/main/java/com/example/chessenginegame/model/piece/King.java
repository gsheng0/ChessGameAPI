package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.piece.Piece;

import java.util.Arrays;
import java.util.List;

public class King extends Piece {
    public King(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Arrays.asList(-7, 7, -9, 9, -8, 8, -1, 1);
    }
}