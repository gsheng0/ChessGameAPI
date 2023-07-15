package com.example.chessenginegame.components;

import java.util.ArrayList;

public class King extends Piece{
    public King(String color, int tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Integer> getMoveShifts() {
        return null;
    }
}
