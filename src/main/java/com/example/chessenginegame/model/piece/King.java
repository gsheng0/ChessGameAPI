package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.util.TileUtil;

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

    public List<Integer> moveShifts(int tile) {
        int file = TileUtil.getFile(tile);
        int rank = TileUtil.getRank(tile);
        List<Integer> moveShifts = SlidingPiece.getAllMoveShifts();
        if(file == 0){
            moveShifts.remove(Constants.LEFT);
            moveShifts.remove(Constants.LEFT_DOWN);
            moveShifts.remove(Constants.LEFT_UP);
        }
        else if(file == Board.LENGTH - 1){
            moveShifts.remove(Constants.RIGHT_DOWN);
            moveShifts.remove(Constants.RIGHT);
            moveShifts.remove(Constants.RIGHT_UP);
        }
        if (rank == 0) {
            moveShifts.remove(Constants.UP);
            moveShifts.remove(Constants.LEFT_UP);
            moveShifts.remove(Constants.RIGHT_UP);
        } else if (rank ==  Board.LENGTH - 1) {
            moveShifts.remove(Constants.DOWN);
            moveShifts.remove(Constants.LEFT_DOWN);
            moveShifts.remove(Constants.RIGHT_DOWN);
        }
        return moveShifts;
    }

}
