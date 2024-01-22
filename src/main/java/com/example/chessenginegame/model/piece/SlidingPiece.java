package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SlidingPiece extends Piece{
    public SlidingPiece(String color){
        super(color);
    }
    public abstract List<Integer> getMoveShifts();
//    @Override
//    public abstract List<Integer> moveShifts(int tile);

    public static List<Integer> getStraightMoveShifts() {
        return new ArrayList<>(Arrays.asList(
                Constants.DOWN,
                Constants.UP,
                Constants.LEFT,
                Constants.RIGHT
        ));
    }

    public static List<Integer> getDiagMoveShifts() {
        return new ArrayList<>(Arrays.asList(
                Constants.RIGHT_DOWN,
                Constants.LEFT_UP,
                Constants.LEFT_DOWN,
                Constants.RIGHT_UP
        ));
    }

    public static List<Integer> getAllMoveShifts() {
        List<Integer> sms = getStraightMoveShifts();
        sms.addAll(getDiagMoveShifts());
        return sms;
    }

}
