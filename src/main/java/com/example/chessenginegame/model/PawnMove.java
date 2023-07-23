package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.Pawn;

public class PawnMove extends Move{
    private boolean isCapture;
    public PawnMove(Pawn pawn, int startTile, int endTile, boolean isCapture){
        super(pawn, startTile, endTile);
        this.isCapture = isCapture;
    }
    public boolean isCapture(){
        return isCapture;
    }
}
