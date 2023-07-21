package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.Piece;

public class Pin {
    public final Piece pinnedPiece;
    public final Piece pinningPiece;
    public final int direction;

    public Pin(Piece pinnedPiece, Piece pinningPiece, int direction) {
        this.pinnedPiece = pinnedPiece;
        this.pinningPiece = pinningPiece;
        this.direction = direction;
    }

}
