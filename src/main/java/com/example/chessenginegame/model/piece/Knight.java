package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.piece.Piece;

import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {
    public Knight(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17);
    }
}
