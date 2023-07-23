package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.util.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.Arrays;
import java.util.List;

public class Knight extends Piece {
    public Knight(String color) {
        super(color);
    }

    @Override
    public List<Integer> getMoveShifts() {
        return Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17);
    }
    public static List<Integer> moveShifts(int tile) {
        return getMoveShiftsFromFile(TileUtil.getFile(tile));
    }

    private static List<Integer> getMoveShiftsFromFile(int file) {
        if(file == 0) {
            return Arrays.asList(-15, -6, 10, 17);
        } else if(file == 1){
            return Arrays.asList(-17, -15, -6, 10, 15, 17);
        } else if(file == 6){
            return Arrays.asList(-17, -15, -10, 6, 15, 17);
        } else if(file == 7){
            return Arrays.asList(-17, -10, 6, 15);
        }
        return Arrays.asList(-17, -15, -10, -6, 6, 10, 15, 17);
    }
}
