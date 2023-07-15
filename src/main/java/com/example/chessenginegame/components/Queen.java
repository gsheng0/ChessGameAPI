package com.example.chessenginegame.components;

import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(String color, int tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Integer> getMoveShifts() {
        return null;
    }
}
