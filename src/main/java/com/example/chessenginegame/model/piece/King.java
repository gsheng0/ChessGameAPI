package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends Piece {

    public King(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "King";
    }
    @Override
    public int getValue() {
        return 10;
    }
    @Override
    public char toChar() {
        if (getColor().equals(Constants.WHITE)) {
            return 'K';
        }
        return 'k';
    }
    @Override
    public String toAbv() {
        //for drawing to System.out
        if (getColor().equals(Constants.WHITE)) {
            return "wK";
        }
        return "bK";
    }
    @Override
    public List<Integer> moveShifts(int tile) {
        int file = TileUtil.getFile(tile);
        int rank = TileUtil.getRank(tile);
        List<Integer> moveShifts = new ArrayList<>(Arrays.asList(
                Constants.RIGHT_DOWN,
                Constants.LEFT_UP,
                Constants.LEFT_DOWN,
                Constants.RIGHT_UP,
                Constants.DOWN,
                Constants.UP,
                Constants.LEFT,
                Constants.RIGHT));
        if(file == 0){
            moveShifts.remove(Constants.LEFT);
            moveShifts.remove(Constants.LEFT_DOWN);
            moveShifts.remove(Constants.LEFT_UP);
        }
        else if(file == 7){
            moveShifts.remove(Constants.RIGHT_DOWN);
            moveShifts.remove(Constants.RIGHT);
            moveShifts.remove(Constants.RIGHT_UP);
        }
        if (rank == 0) {
            moveShifts.remove(Constants.DOWN);
            moveShifts.remove(Constants.LEFT_DOWN);
            moveShifts.remove(Constants.RIGHT_DOWN);
        } else if (rank == 7) {
            moveShifts.remove(Constants.UP);
            moveShifts.remove(Constants.LEFT_UP);
            moveShifts.remove(Constants.RIGHT_UP);
        }
        return moveShifts;
    }

}
