package com.example.chessenginegame.model.piece;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void testDiagMoveShifts_OK() {
        Piece bishop = (Bishop)new Piece.PieceBuilder().bishop().black().build();
        List<Integer> moveShifts = bishop.diagMoveShifts(19);
        assertTrue(moveShifts.contains(28));
    }
}