package com.example.chessenginegame.model.piece;

import java.util.List;

public abstract class SlidingPiece extends Piece{
    public SlidingPiece(String color){
        super(color);
    }
    public abstract List<Integer> getMoveShifts();
}
