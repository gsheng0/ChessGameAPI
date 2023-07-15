package com.example.chessenginegame.components;

import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(String color, int tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Integer> getMoveShifts() {
        return null;
    }
}
