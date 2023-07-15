package com.example.chessenginegame.components;

import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(String color, int tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Integer> getMoveShifts() {
        return null;
    }
}
