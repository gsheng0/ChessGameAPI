package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.util.Constants;
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
        if (getColor().equals(Constants.WHITE)) {
            return "wK";
        }
        return "bK";
    }
    public static List<Integer> moveShifts(int tile) {
        int file = TileUtil.getFile(tile);
        List<Integer> moveShifts = new ArrayList<>(Arrays.asList(-7, 7, -9, 9, -8, 8, -1, 1));
        if(file == 0){
            moveShifts.remove(Integer.valueOf(-1));
            moveShifts.remove(Integer.valueOf(-9));
            moveShifts.remove(Integer.valueOf(7));
        }
        else if(file == 7){
            moveShifts.remove(Integer.valueOf(-7));
            moveShifts.remove(Integer.valueOf(1));
            moveShifts.remove(Integer.valueOf(9));
        }
        return moveShifts;
    }
}
