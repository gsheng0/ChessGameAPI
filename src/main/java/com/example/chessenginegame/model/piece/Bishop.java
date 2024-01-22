package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bishop extends SlidingPiece {
    public Bishop(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "Bishop";
    }
    @Override
    public int getValue() {
        return 3;
    }
    @Override
    public char toChar() {
        if(getColor().equals(Constants.WHITE)){
            return 'B';
        }
        return 'b';
    }
    @Override
    public String toAbv() {
        if(getColor().equals(Constants.WHITE)){
            return "wB";
        }
        return "bB";
    }

//    public List<Integer> moveShifts(int tile){
//        return diagMoveShifts(tile);
//    }

    @Override
    public List<Integer> getMoveShifts() {
        return getDiagMoveShifts();
    }
}
