package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.chessenginegame.model.Board.LENGTH;

public class Knight extends Piece {
    public Knight(String color) {
        super(color);
    }
    @Override
    public String getName() {
        return "Knight";
    }
    @Override
    public int getValue() {
        return 3;
    }
    @Override
    public char toChar() {
        if (getColor().equals(Constants.WHITE)) {
            return 'N';
        }
        return 'n';
    }
    @Override
    public String toAbv() {
        if (getColor().equals(Constants.WHITE)) {
            return "wN";
        }
        return "bN";
    }

    public List<Integer> moveShifts(int tile) {
        return getMoveShifts(tile);
    }

    public static List<Integer> getMoveShifts(int tile) {
        int file = TileUtil.getFile(tile);
        int rank = TileUtil.getRank(tile);
        List<Integer> ms = new ArrayList<>(Arrays.asList(
                Constants.LEFT2_DOWN,
                Constants.LEFT_DOWN2,
                Constants.LEFT2_UP,
                Constants.LEFT_UP2,
                Constants.RIGHT2_DOWN,
                Constants.RIGHT_DOWN2,
                Constants.RIGHT2_UP,
                Constants.RIGHT_UP2
        ));
        if(file == 0) {
            ms.remove(Constants.LEFT2_DOWN);
            ms.remove(Constants.LEFT2_UP);
            ms.remove(Constants.LEFT_DOWN2);
            ms.remove(Constants.LEFT_UP2);
        } else if(file == 1){
            ms.remove(Constants.LEFT2_DOWN);
            ms.remove(Constants.LEFT2_UP);
        } else if(file == LENGTH - 2){
            ms.remove(Constants.RIGHT2_DOWN);
            ms.remove(Constants.RIGHT2_UP);
        } else if(file == LENGTH - 1){
            ms.remove(Constants.RIGHT2_DOWN);
            ms.remove(Constants.RIGHT2_UP);
            ms.remove(Constants.RIGHT_DOWN2);
            ms.remove(Constants.RIGHT_UP2);
        }
        if (rank == 0) {
            ms.remove(Constants.LEFT2_UP);
            ms.remove(Constants.LEFT_UP2);
            ms.remove(Constants.RIGHT2_UP);
            ms.remove(Constants.RIGHT_UP2);
        } else if (rank == 1) {
            ms.remove(Constants.LEFT_UP2);
            ms.remove(Constants.RIGHT_UP2);
        } else if (rank == LENGTH - 2) {
            ms.remove(Constants.LEFT_DOWN2);
            ms.remove(Constants.RIGHT_DOWN2);
        } else if (rank == LENGTH - 1) {
            ms.remove(Constants.LEFT_DOWN2);
            ms.remove(Constants.LEFT2_DOWN);
            ms.remove(Constants.RIGHT_DOWN2);
            ms.remove(Constants.RIGHT2_DOWN);
        }
        return ms;
    }
}
