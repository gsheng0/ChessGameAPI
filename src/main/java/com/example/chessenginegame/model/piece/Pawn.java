package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.util.Constants;

import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(String color, int tile) {
        super(color, tile);
    }

    @Override
    public List<Integer> getMoveShifts() {
        int multiplier = getDirectionMultiplier(this.getColor());
        List<Integer> moveShifts = Arrays.asList(multiplier * 8, multiplier * 7, multiplier * 9);
        if(!this.hasMoved()){
            moveShifts.add(multiplier * 16);
        }
        return moveShifts;
    }
    public static int getDirectionMultiplier(String color){
        return color.equals(Constants.WHITE) ? -1 : 1;
    }
}
