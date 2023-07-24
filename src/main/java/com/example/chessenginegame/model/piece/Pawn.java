package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.util.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece{
    public Pawn(String color) {
        super(color);
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
    public static List<Integer> getMoveShifts(int tile, String color){
        int multiplier = getDirectionMultiplier(color);
        List<Integer> moveShifts = Arrays.asList(multiplier * 8, multiplier * 7, multiplier * 9);
        int file = TileUtil.getFile(tile);
        if(file == 0){
            moveShifts.remove(Integer.valueOf(-9));
            moveShifts.remove(Integer.valueOf(7));
        } else if(file == 7){
            moveShifts.remove(Integer.valueOf(-7));
            moveShifts.remove(Integer.valueOf(9));
        }
        return moveShifts;
    }

    public static int getDirectionMultiplier(String color){
        return color.equals(Constants.WHITE) ? -1 : 1;
    }
}
