package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece {
    private final int directionMultiplier;

    public Pawn(String color) {
        super(color);
        directionMultiplier =  getDirectionMultiplier(color);
    }
    public int getDirectionMultiplier() { return directionMultiplier; }

    @Override
    public String getName() {
        return "Pawn";
    }

    @Override
    public int getValue() {
        return 1;
    }

    @Override
    public char toChar() {
        if (getColor().equals(Constants.WHITE)) {
            return 'P';
        }
        return 'p';
    }

    @Override
    public String toAbv() {
        if (getColor().equals(Constants.WHITE)) {
            return "wP";
        }
        return "bP";
    }

    public static List<Integer> captureMoveShifts(int tile, String color) {
        int multiplier = getDirectionMultiplier(color);
        List<Integer> moveShifts = new ArrayList<>(Arrays.asList(Constants.LEFT_DOWN * multiplier, Constants.RIGHT_DOWN * multiplier));
        int file = TileUtil.getFile(tile);
        if(file == 0){
            moveShifts.remove(Constants.LEFT_DOWN);
            moveShifts.remove(Constants.LEFT_UP);
        } else if(file == 7){
            moveShifts.remove(Constants.RIGHT_DOWN);
            moveShifts.remove(Constants.RIGHT_UP);
        }
        return moveShifts;
    }
    private static int getDirectionMultiplier(String color) {
        return color.equals(Constants.WHITE) ? -1 : 1;
    }
}
