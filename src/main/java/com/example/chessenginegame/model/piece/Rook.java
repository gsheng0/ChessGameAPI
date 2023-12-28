package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.util.Constants;

import java.util.Arrays;
import java.util.List;

public class Rook extends SlidingPiece{
    public Rook(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "Rook";
    }
    @Override
    public int getValue() {
        return 5;
    }
    @Override
    public char toChar() {
        if (getColor().equals(Constants.WHITE)) {
            return 'R';
        }
        return 'r';
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Rook.moveShifts();
    }
    public static List<Integer> moveShifts() { return Arrays.asList(-1, 1, 8, -8);}
}
