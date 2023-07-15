package com.example.chessenginegame.components;

import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(String color, int tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Integer> getMoveShifts() {
        return null;
    }
}
