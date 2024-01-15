package com.example.chessenginegame.model.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    void moveShifts() {
        King king = (King)new Piece.PieceBuilder().king().black().build();

        // regular
        assertEquals (king.moveShifts(34).size(), 8);
        // corners
        assertEquals (king.moveShifts(0).size(), 3);
        assertEquals (king.moveShifts(7).size(), 3);
        assertEquals (king.moveShifts(56).size(), 3);
        assertEquals (king.moveShifts(63).size(), 3);
        // sides
        assertEquals (king.moveShifts(24).size(), 5);
        assertEquals (king.moveShifts(59).size(), 5);
        assertEquals (king.moveShifts(39).size(), 5);
        assertEquals (king.moveShifts(3).size(), 5);
    }
}