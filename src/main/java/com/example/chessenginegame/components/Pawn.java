package com.example.chessenginegame.components;

import java.util.ArrayList;

public class Pawn extends Piece{
    private Pawn(){
        super();
    }
    public Pawn(String color, int tile) {
        super(color, tile);
    }

    @Override
    public ArrayList<Integer> getMoveShifts() {
        return null;
    }
}
