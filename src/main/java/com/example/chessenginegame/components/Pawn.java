package com.example.chessenginegame.components;

import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        int multiplier = this.getColor().equals("WHITE") ? -1 : 1;
        List<Integer> moveShifts = Arrays.asList(multiplier * 8, multiplier * 7, multiplier * 9);
        if(!this.hasMoved()){
            moveShifts.add(multiplier * 16);
        }
        return moveShifts;
    }
}
