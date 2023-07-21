package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.Piece;

public class Pin {
    public final Piece pinned;
    public final Piece pinning;

    public Pin(Piece pinned, Piece pinning) {
        this.pinned = pinned;
        this.pinning = pinning;
    }

}
