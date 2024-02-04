package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.*;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveGeneratorServiceImplUnitTest extends MoveGeneratorServiceImplTest {
    @Test
    public void PawnOnEmptyBoard_1() {
        Piece whitePawn = Piece.of('P');
        testSinglePieceMoveGeneration(whitePawn, 51, 43, 35);
    }
    @Test
    public void PawnOnEmptyBoard_2() {
        Piece whitePawn = Piece.of('P');
        testSinglePieceMoveGeneration(whitePawn, 32, 24);
    }
    @Test
    public void PawnOnEmptyBoard_3() {
        Piece whitePawn = Piece.of('P');
        testSinglePieceMoveGeneration(whitePawn, 55, 47, 39);
    }
    @Test
    public void PawnOnEmptyBoard_4() {
        Piece whitePawn = Piece.of('P');
        testSinglePieceMoveGeneration(whitePawn, 35, 27);
    }
    @Test
    public void PawnOnEmptyBoard_5() {
        Piece blackPawn = Piece.of('p');
        testSinglePieceMoveGeneration(blackPawn, 8, 16, 24);
    }
    @Test
    public void PawnOnEmptyBoard_6() {
        Piece blackPawn = Piece.of('p');
        testSinglePieceMoveGeneration(blackPawn, 15, 23, 31);
    }
    @Test
    public void PawnOnEmptyBoard_7() {
        Piece blackPawn = Piece.of('p');
        testSinglePieceMoveGeneration(blackPawn, 20, 28);
    }
    @Test
    public void PawnOnEmptyBoard_8() {
        Piece blackPawn = Piece.of('p');
        testSinglePieceMoveGeneration(blackPawn, 35, 43);
    }
    @Test
    public void KnightOnEmptyBoard_1() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
    }
    @Test
    public void KnightOnEmptyBoard_2() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 49, 32, 34, 43, 59);
    }
    @Test
    public void KnightOnEmptyBoard_3() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 1, 11, 16, 18);
    }
    @Test
    public void KnightOnEmptyBoard_4() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 7, 13, 22);
    }
    @Test
    public void KnightOnEmptyBoard_5() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 39, 22, 29, 45, 54);
    }
    @Test
    public void KnightOnEmptyBoard_6() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 24, 9, 18, 34, 41);
    }
    @Test
    public void KnightOnEmptyBoard_7() {
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 61, 44, 46, 51, 55);
    }
    @Test
    public void KnightOnEmptyBoard_8() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
    }
    @Test
    public void KnightOnEmptyBoard_9() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 49, 32, 34, 43, 59);
    }
    @Test
    public void KnightOnEmptyBoard_10() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 1, 11, 16, 18);
    }
    @Test
    public void KnightOnEmptyBoard_11() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 7, 13, 22);
    }
    @Test
    public void KnightOnEmptyBoard_12() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 39, 22, 29, 45, 54);
    }
    @Test
    public void KnightOnEmptyBoard_13() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 24, 9, 18, 34, 41);
    }
    @Test
    public void KnightOnEmptyBoard_14() {
        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 61, 44, 46, 51, 55);
    }
    @Test
    public void BishopOnEmptyBoard_1() {
        Piece whiteBishop = Piece.of('B');
        testSinglePieceMoveGeneration(whiteBishop, 56, 49, 42, 35, 28, 21, 14, 7);
    }
    @Test
    public void BishopOnEmptyBoard_2() {
        Piece whiteBishop = Piece.of('B');
        testSinglePieceMoveGeneration(whiteBishop, 63, 54, 45, 36, 27, 18, 9, 0);
    }
    @Test
    public void BishopOnEmptyBoard_3() {
        Piece whiteBishop = Piece.of('B');
        testSinglePieceMoveGeneration(whiteBishop, 35, 56, 49, 42, 28, 21, 14, 7, 8, 17, 26, 44, 53, 62);
    }
    @Test
    public void BishopOnEmptyBoard_4() {
        Piece whiteBishop = Piece.of('B');
        testSinglePieceMoveGeneration(whiteBishop, 36, 57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
    }
    @Test
    public void BishopOnEmptyBoard_5() {
        Piece whiteBishop = Piece.of('B');
        testSinglePieceMoveGeneration(whiteBishop, 10, 1, 19, 28, 37, 46, 55, 3, 17, 24);
    }
    @Test
    public void BishopOnEmptyBoard_6() {
        Piece blackBishop = Piece.of('b');
        testSinglePieceMoveGeneration(blackBishop, 56, 49, 42, 35, 28, 21, 14, 7);
    }
    @Test
    public void BishopOnEmptyBoard_7() {
        Piece blackBishop = Piece.of('b');
        testSinglePieceMoveGeneration(blackBishop, 63, 54, 45, 36, 27, 18, 9, 0);
    }
    @Test
    public void BishopOnEmptyBoard_8() {
        Piece blackBishop = Piece.of('b');
        testSinglePieceMoveGeneration(blackBishop, 35, 56, 49, 42, 28, 21, 14, 7, 8, 17, 26, 44, 53, 62);
    }
    @Test
    public void BishopOnEmptyBoard_9() {
        Piece blackBishop = Piece.of('b');
        testSinglePieceMoveGeneration(blackBishop, 36, 57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
    }
    @Test
    public void BishopOnEmptyBoard_10() {
        Piece blackBishop = Piece.of('b');
        testSinglePieceMoveGeneration(blackBishop, 10, 1, 19, 28, 37, 46, 55, 3, 17, 24);
    }
    @Test
    public void RookOnEmptyBoard_1() {
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63);
    }
    @Test
    public void RookOnEmptyBoard_2() {
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 7, 6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63);
    }
    @Test
    public void RookOnEmptyBoard_3() {
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39);
    }
    @Test
    public void RookOnEmptyBoard_4() {
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 25, 24, 26, 27, 28, 29, 30, 31, 1, 9, 17, 33, 41, 49, 57);
    }
    @Test
    public void RookOnEmptyBoard_5() {
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 54, 6, 14, 22, 30, 38, 46, 62, 48, 49, 50, 51, 52, 53, 55);
    }
    @Test
    public void RookOnEmptyBoard_6() {
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 1, 0, 2, 3, 4, 5, 6, 7, 9, 17, 25, 33, 41, 49, 57);
    }
    @Test
    public void RookOnEmptyBoard_7() {
        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63);
    }
    @Test
    public void RookOnEmptyBoard_8() {
        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 7, 6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63);
    }
    @Test
    public void RookOnEmptyBoard_9() {
        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39);
    }
    @Test
    public void RookOnEmptyBoard_10() {
        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 25, 24, 26, 27, 28, 29, 30, 31, 1, 9, 17, 33, 41, 49, 57);
    }
    @Test
    public void RookOnEmptyBoard_11() {
        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 54, 6, 14, 22, 30, 38, 46, 62, 48, 49, 50, 51, 52, 53, 55);
    }
    @Test
    public void RookOnEmptyBoard_12(){
        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 1, 0, 2, 3, 4, 5, 6, 7, 9, 17, 25, 33, 41, 49, 57);
    }
    @Test
    public void QueenOnEmptyBoard_1() {
        Piece whiteQueen = Piece.of('Q');
        testSinglePieceMoveGeneration(whiteQueen, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63,
                49, 42, 35, 28, 21, 14, 7);
    }
    @Test
    public void QueenOnEmptyBoard_2() {
        Piece whiteQueen = Piece.of('Q');
        testSinglePieceMoveGeneration(whiteQueen, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39,
                57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
    }
    @Test
    public void QueenOnEmptyBoard_3() {
        Piece whiteQueen = Piece.of('Q');
        testSinglePieceMoveGeneration(whiteQueen, 62, 6, 14, 22, 30, 38, 46, 54, 56, 57, 58, 59, 60, 61, 63,
                53, 44, 35, 26, 17, 8, 55);
    }
    @Test
    public void QueenOnEmptyBoard_4() {
        Piece whiteQueen = Piece.of('Q');
        testSinglePieceMoveGeneration(whiteQueen, 11, 3, 19, 27, 35, 43, 51, 59, 8, 9, 10, 12, 13, 14, 15,
                2, 20, 29, 38, 47, 4, 18, 25, 32);
    }
    @Test
    public void QueenOnEmptyBoard_5() {
        Piece whiteQueen = Piece.of('Q');
        testSinglePieceMoveGeneration(whiteQueen, 58, 50, 42, 34, 26, 18, 10, 2, 56, 57, 59, 60, 61, 62, 63,
                40, 49, 23, 30, 37, 44, 51);
    }
    @Test
    public void QueenOnEmptyBoard_6() {
        Piece blackQueen = Piece.of('q');
        testSinglePieceMoveGeneration(blackQueen, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63,
                49, 42, 35, 28, 21, 14, 7);
    }
    @Test
    public void QueenOnEmptyBoard_7() {
        Piece blackQueen = Piece.of('q');
        testSinglePieceMoveGeneration(blackQueen, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39,
                57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
    }
    @Test
    public void QueenOnEmptyBoard_8() {
        Piece blackQueen = Piece.of('q');
        testSinglePieceMoveGeneration(blackQueen, 62, 6, 14, 22, 30, 38, 46, 54, 56, 57, 58, 59, 60, 61, 63,
                53, 44, 35, 26, 17, 8, 55);
    }
    @Test
    public void QueenOnEmptyBoard_9() {
        Piece blackQueen = Piece.of('q');
        testSinglePieceMoveGeneration(blackQueen, 11, 3, 19, 27, 35, 43, 51, 59, 8, 9, 10, 12, 13, 14, 15,
                2, 20, 29, 38, 47, 4, 18, 25, 32);
    }
    @Test
    public void QueenOnEmptyBoard_10(){
        Piece blackQueen = Piece.of('q');
        testSinglePieceMoveGeneration(blackQueen, 58, 50, 42, 34, 26, 18, 10, 2, 56, 57, 59, 60, 61, 62, 63,
                40, 49, 23, 30, 37, 44, 51);
    }
    @Test
    public void KingOnEmptyBoard_1() {
        Piece whiteKing = Piece.of('K');
        testSinglePieceMoveGeneration(whiteKing, 60, 51, 52, 53, 59, 61);
    }
    @Test
    public void KingOnEmptyBoard_2() {
        Piece whiteKing = Piece.of('K');
        testSinglePieceMoveGeneration(whiteKing, 27, 18, 19, 20, 26, 28, 34, 35, 36);
    }
    @Test
    public void KingOnEmptyBoard_3() {
        Piece whiteKing = Piece.of('K');
        testSinglePieceMoveGeneration(whiteKing, 39, 30, 31, 38, 46, 47);
    }
    @Test
    public void KingOnEmptyBoard_4() {
            Piece whiteKing = Piece.of('K');
            testSinglePieceMoveGeneration(whiteKing, 40, 32, 33, 41, 48, 49);
    }
    @Test
    public void KingOnEmptyBoard_5() {
        Piece blackKing = Piece.of('k');
        testSinglePieceMoveGeneration(blackKing, 60, 51, 52, 53, 59, 61);
    }
    @Test
    public void KingOnEmptyBoard_6() {
        Piece blackKing = Piece.of('k');
        testSinglePieceMoveGeneration(blackKing, 27, 18, 19, 20, 26, 28, 34, 35, 36);
    }
    @Test
    public void KingOnEmptyBoard_7() {
        Piece blackKing = Piece.of('k');
        testSinglePieceMoveGeneration(blackKing, 39, 30, 31, 38, 46, 47);
    }
    @Test
    public void KingOnEmptyBoard_8() {
        Piece blackKing = Piece.of('k');
        testSinglePieceMoveGeneration(blackKing, 40, 32, 33, 41, 48, 49);
    }
    @Test
    public void NoChecks_1() {
        testGetAttackerCount(Constants.WHITE, 60, "r", 51, 0);
    }
    @Test
    public void NoChecks_2() {
        testGetAttackerCount(Constants.WHITE, 60, "n", 51, 0);
    }
    @Test
    public void NoChecks_3() {
        testGetAttackerCount(Constants.WHITE, 43, "b", 35, 0);
    }
    @Test
    public void NoChecks_4() {
        testGetAttackerCount(Constants.WHITE, 43, "p", 35, 0);
    }
    @Test
    public void NoChecks_5() {
        testGetAttackerCount(Constants.WHITE, 43, "q", 43, 0);
    }
    @Test
    public void NoChecks_6() {
        testGetAttackerCount(Constants.WHITE, 9, "q", 3, 0);
    }
    @Test
    public void NoChecks_7() {
        testGetAttackerCount(Constants.WHITE, 6, "b", 14, 0);
    }
    @Test
    public void NoChecks_8() {
        testGetAttackerCount(Constants.WHITE, 15, "r", 20, 0);
    }
    @Test
    public void NoChecks_9() {
        testGetAttackerCount(Constants.WHITE, 19, "p", 18, 0);
    }
    @Test
    public void NoChecks_10() {
        testGetAttackerCount(Constants.WHITE, 39, "n", 24, 0);
    }
    @Test
    public void NoChecks_11() {
        testGetAttackerCount(Constants.WHITE, 32, "p", 23, 0);
    }
    @Test
    public void NoChecks_12() {
        testGetAttackerCount(Constants.WHITE, 48, "b", 39, 0);
    }
    @Test
    public void NoChecks_13() {
        testGetAttackerCount(Constants.WHITE, 48, "p", 39, 0);
    }
    @Test
    public void NoChecks_14() {
        testGetAttackerCount(Constants.WHITE, 8, "q", 25, 0);
    }
    @Test
    public void NoChecks_15() {
        testGetAttackerCount(Constants.WHITE, 5, "p", 14, 0);
    }
    @Test
    public void NoChecks_16() {
        testGetAttackerCount(Constants.WHITE, 0, "b", 7, 0);
    }

    @Test
    public void NoChecks_17() {
        testGetAttackerCount(Constants.BLACK, 60, "R", 51, 0);
    }
    @Test
    public void NoChecks_18() {
        testGetAttackerCount(Constants.BLACK, 60, "N", 51, 0);
    }
    @Test
    public void NoChecks_19() {
        testGetAttackerCount(Constants.BLACK, 43, "B", 35, 0);
    }
    @Test
    public void NoChecks_20() {
        testGetAttackerCount(Constants.BLACK, 43, "P", 35, 0);
    }
    @Test
    public void NoChecks_21() {
        testGetAttackerCount(Constants.BLACK, 43, "Q", 43, 0);
    }
    @Test
    public void NoChecks_22() {
        testGetAttackerCount(Constants.BLACK, 9, "Q", 3, 0);
    }
    @Test
    public void NoChecks_23() {
        testGetAttackerCount(Constants.BLACK, 6, "B", 14, 0);
    }
    @Test
    public void NoChecks_24() {
        testGetAttackerCount(Constants.BLACK, 15, "R", 20, 0);
    }
    @Test
    public void NoChecks_25() {
        testGetAttackerCount(Constants.BLACK, 19, "P", 18, 0);
    }
    @Test
    public void NoChecks_26() {
        testGetAttackerCount(Constants.BLACK, 39, "N", 24, 0);
    }
    @Test
    public void NoChecks_27() {
        testGetAttackerCount(Constants.BLACK, 32, "P", 23, 0);
    }
    @Test
    public void NoChecks_28() {
        testGetAttackerCount(Constants.BLACK, 48, "B", 39, 0);
    }
    @Test
    public void NoChecks_29() {
        testGetAttackerCount(Constants.BLACK, 48, "P", 39, 0);
    }
    @Test
    public void NoChecks_30() {
        testGetAttackerCount(Constants.BLACK, 8, "Q", 25, 0);
    }
    @Test
    public void NoChecks_31() {
        testGetAttackerCount(Constants.BLACK, 61, "P", 54, 0);
    }
    @Test
    public void NoChecks_32(){
        testGetAttackerCount(Constants.BLACK, 0, "B", 7, 0);
    }
    @Test
    public void SinglePieceChecks_1() {
        testGetAttackerCount(Constants.WHITE, 60, "r", 56, 1);
    }
    @Test
    public void SinglePieceChecks_2() {
        testGetAttackerCount(Constants.WHITE, 10, "b", 55, 1);
    }
    @Test
    public void SinglePieceChecks_3() {
        testGetAttackerCount(Constants.WHITE, 34, "n", 44, 1);
    }
    @Test
    public void SinglePieceChecks_4(){
        testGetAttackerCount(Constants.WHITE, 54, "p", 45, 1);
    }
    @Test
    public void SinglePieceChecks_5() {
        testGetAttackerCount(Constants.WHITE, 35, "p", 28, 1);
    }
    @Test
    public void SinglePieceChecks_6() {
        testGetAttackerCount(Constants.WHITE, 18, "p", 11, 1);
    }
    @Test
    public void SinglePieceChecks_7() {
        testGetAttackerCount(Constants.WHITE, 4, "q", 32, 1);
    }
    @Test
    public void SinglePieceChecks_8() {
        testGetAttackerCount(Constants.WHITE, 9, "r", 57, 1);
    }
    @Test
    public void SinglePieceChecks_9() {
        testGetAttackerCount(Constants.WHITE, 7, "b", 56, 1);
    }
    @Test
    public void SinglePieceChecks_10() {
        testGetAttackerCount(Constants.WHITE, 32, "r", 39, 1);
    }
    @Test
    public void SinglePieceChecks_11() {
        testGetAttackerCount(Constants.BLACK, 60, "R", 56, 1);
    }
    @Test
    public void SinglePieceChecks_12() {
        testGetAttackerCount(Constants.BLACK, 10, "B", 55, 1);
    }
    @Test
    public void SinglePieceChecks_13() {
        testGetAttackerCount(Constants.BLACK, 34, "N", 44, 1);
    }
    @Test
    public void SinglePieceChecks_14() {
        testGetAttackerCount(Constants.BLACK, 5, "P", 14, 1);
    }
    @Test
    public void SinglePieceChecks_15() {
        testGetAttackerCount(Constants.BLACK, 28, "P", 35, 1);
    }
    @Test
    public void SinglePieceChecks_16() {
        testGetAttackerCount(Constants.BLACK, 11, "P", 18, 1);
    }
    @Test
    public void SinglePieceChecks_17() {
        testGetAttackerCount(Constants.BLACK, 4, "Q", 32, 1);
    }
    @Test
    public void SinglePieceChecks_18() {
        testGetAttackerCount(Constants.BLACK, 9, "R", 57, 1);
    }
    @Test
    public void SinglePieceChecks_19() {
        testGetAttackerCount(Constants.BLACK, 7, "B", 56, 1);
    }
    @Test
    public void SinglePieceChecks_20(){
/*
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
bK  ()  ()  ()  ()  ()  ()  wR
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
 */
        testGetAttackerCount(Constants.BLACK, 32, "R", 39, 1).printBoardMatrix();
    }
    @Test
    public void DoublePieceChecks_1() {
        testGetAttackerCount(Constants.WHITE, 27, "p", 18, "p", 20, 2);
    }
    @Test
    public void DoublePieceChecks_2() {
        testGetAttackerCount(Constants.WHITE, 2, "n", 17, "n", 19, 2);
    }
    @Test
    public void DoublePieceChecks_3() {
        testGetAttackerCount(Constants.WHITE, 30, "b", 23, "b", 51, 2);
    }
    @Test
    public void DoublePieceChecks_4() {
        testGetAttackerCount(Constants.WHITE, 53, "r", 5, "r", 48, 2);
    }
    @Test
    public void DoublePieceChecks_5() {
        testGetAttackerCount(Constants.WHITE, 57, "q", 15, "q", 25, 2);
    }
    @Test
    public void DoublePieceChecks_6() {
        testGetAttackerCount(Constants.WHITE, 62, "p", 55, "r", 14, 2);
    }
    @Test
    public void DoublePieceChecks_7() {
        testGetAttackerCount(Constants.WHITE, 16, "n", 26, "b", 52, 2);
    }
    @Test
    public void DoublePieceChecks_8() {
        testGetAttackerCount(Constants.WHITE, 25, "q", 4, "n", 8, 2);
    }
    @Test
    public void DoublePieceChecks_9() {
        testGetAttackerCount(Constants.WHITE, 41, "b", 13, "r", 46, 2);
    }
    @Test
    public void DoublePieceChecks_10() {
        testGetAttackerCount(Constants.WHITE, 39, "n", 22, "q", 33, 2);
    }
    @Test
    public void DoublePieceChecks_11() {
        testGetAttackerCount(Constants.BLACK, 27, "P", 34, "P", 36, 2);
    }
    @Test
    public void DoublePieceChecks_12() {
        testGetAttackerCount(Constants.BLACK, 2, "N", 17, "N", 19, 2);
    }
    @Test
    public void DoublePieceChecks_13() {
        testGetAttackerCount(Constants.BLACK, 30, "B", 23, "B", 51, 2);
    }
    @Test
    public void DoublePieceChecks_14() {
        testGetAttackerCount(Constants.BLACK, 53, "R", 5, "R", 48, 2);
    }
    @Test
    public void DoublePieceChecks_15() {
        testGetAttackerCount(Constants.BLACK, 57, "Q", 15, "Q", 25, 2);
    }
    @Test
    public void DoublePieceChecks_16() {
        testGetAttackerCount(Constants.BLACK, 46, "P", 55, "R", 14, 2);
    }
    @Test
    public void DoublePieceChecks_17() {
        testGetAttackerCount(Constants.BLACK, 16, "N", 26, "B", 52, 2);
    }
    @Test
    public void DoublePieceChecks_18() {
        testGetAttackerCount(Constants.BLACK, 25, "Q", 4, "N", 8, 2);
    }
    @Test
    public void DoublePieceChecks_19() {
        testGetAttackerCount(Constants.BLACK, 41, "B", 13, "R", 46, 2);
    }
    @Test
    public void DoublePieceChecks_20() {
/*
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  wN  ()
()  ()  ()  ()  ()  ()  ()  ()
()  wQ  ()  ()  ()  ()  ()  bK
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
 */
        testGetAttackerCount(Constants.BLACK, 39, "N", 22, "Q", 33, 2).printBoardMatrix();
    }
    @Test
    public void BlockedSingleChecks(){
        testGetAttackerCount(Constants.WHITE, 60, "r", 56, "p", 57, 0);
        testGetAttackerCount(Constants.WHITE, 10, "b", 55, "n", 37, 0);
        testGetAttackerCount(Constants.WHITE, 26, "q", 12, "r", 19, 0);
        testGetAttackerCount(Constants.WHITE, 4, "q", 32, "n", 18, 0);
        testGetAttackerCount(Constants.WHITE, 9, "r", 57, "b", 33, 0);
        testGetAttackerCount(Constants.WHITE, 7, "b", 56, "r", 42, 0);
        testGetAttackerCount(Constants.WHITE, 32, "r", 39, "n", 38, 0);

        testGetAttackerCount(Constants.BLACK, 60, "R", 56, "P", 57, 0);
        testGetAttackerCount(Constants.BLACK, 10, "B", 55, "N", 37, 0);
        testGetAttackerCount(Constants.BLACK, 26, "Q", 12, "R", 19, 0);
        testGetAttackerCount(Constants.BLACK, 4, "Q", 32, "N", 18, 0);
        testGetAttackerCount(Constants.BLACK, 9, "R", 57, "B", 33, 0);
        testGetAttackerCount(Constants.BLACK, 7, "B", 56, "R", 42, 0);
        testGetAttackerCount(Constants.BLACK, 32, "R", 39, "N", 38, 0);
    }
    @Test
    public void PinnedPiece_1() {
        testPinnedPieceMoveGeneration(60, "R", 53, "b", 39);
    }
    @Test
    public void PinnedPiece_2() {
        testPinnedPieceMoveGeneration(60, "B", 53, "b", 39, 46, 39);
    }
    @Test
    public void PinnedPiece_3() {
        testPinnedPieceMoveGeneration(60, "R", 36, "r", 12, 44, 52, 28, 20, 12);
    }
    @Test
    public void PinnedPiece_4() {
        testPinnedPieceMoveGeneration(60, "P", 52, "r", 4, 44, 36);
    }
    @Test
    public void PinnedPiece_5() {
        testPinnedPieceMoveGeneration(60, "B", 52, "q", 20);
    }
    @Test
    public void PinnedPiece_6() {
        testPinnedPieceMoveGeneration(60, "r", 53, "B", 39);
    }
    @Test
    public void PinnedPiece_7() {
        testPinnedPieceMoveGeneration(60, "b", 53, "B", 39, 46, 39);
    }
    @Test
    public void PinnedPiece_8() {
/*
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  wR  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  bR  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  bK  ()  ()  ()
 */
        testPinnedPieceMoveGeneration(60, "r", 36, "R", 12, 44, 52, 28, 20, 12).printBoardMatrix();
    }
    @Test
    public void PinnedPiece_9() {
/*
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  wQ  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  bB  ()  ()  ()
()  ()  ()  ()  bK  ()  ()  ()
 */
        testPinnedPieceMoveGeneration(60, "b", 52, "Q", 20).printBoardMatrix();
    }
    @Test
    public void PinnedPiece_10(){
/*
()  ()  ()  ()  wR  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  ()  ()  ()  ()
()  ()  ()  ()  bP  ()  ()  ()
()  ()  ()  ()  bK  ()  ()  ()
*/
        testPinnedPieceMoveGeneration(60, "p", 52, "R", 4).printBoardMatrix();
    }

    private Board testPinnedPieceMoveGeneration(int kingTile, String pinned, int pinnedTile, String pinning, int pinningTile, int... expected){
        Piece pinnedPiece = Piece.of(pinned);
        Piece pinningPiece = Piece.of(pinning);
        King king = new King(pinnedPiece.getColor());
        Board board = boardBuilder.newBoard().withA(pinnedPiece).onTile(pinnedTile).withA(pinningPiece).onTile(pinningTile).withA(king).onTile(kingTile).build();
        List<Move> expectedMoves = createMoveList(pinnedPiece, pinnedTile, Arrays.stream(expected).boxed().toList());
        List<Move> generatedMoves = moveGenerator.generateLegalMoves(board, pinnedPiece.getColor()).stream().filter(move -> move.getPiece().equals(pinnedPiece)).toList();
        assertEquals(expectedMoves, generatedMoves);
        return board;
    }
    private void testSinglePieceMoveGeneration(Piece piece, int tile, int... expected){
        Board board = boardBuilder.newBoard().withA(piece).onTile(tile).build();
        List<Move> expectedMoves = createMoveList(piece, tile, Arrays.stream(expected).boxed().toList());
        if(piece instanceof Pawn pawn){
            assertEquals(expectedMoves, moveGenerator.generatePawnMoves(pawn, tile, board, null));
        } else if(piece instanceof Knight){
            assertEquals(expectedMoves, moveGenerator.generateKnightMoves((Knight)piece, tile, board, null));
        } else if(piece instanceof SlidingPiece slidingPiece){
            assertEquals(expectedMoves, moveGenerator.generateSlidingPieceMoves(slidingPiece, tile, board, null));
        } else if(piece instanceof King king) {
            assertEquals(expectedMoves, moveGenerator.generateKingMoves1(king, tile, board));
        }
    }
    private Board testGetAttackerCount(String color, int kingTile, String p, int tile, int expected){
        Piece piece = Piece.of(p);
        King king = new King(color);
        Board board = boardBuilder.newBoard().withA(piece).onTile(tile).withA(king).onTile(kingTile).build();
        Assertions.assertEquals(expected, moveGenerator.getAttackersOnKingOfColor(board, color).size());
        return board;
    }
    private Board testGetAttackerCount(String color, int kingTile, String p1, int tile1, String p2, int tile2, int expected){
        King king = new King(color);
        Piece piece1 = Piece.of(p1);
        Piece piece2 = Piece.of(p2);
        Board board = boardBuilder.newBoard().withA(piece1).onTile(tile1).withA(piece2).onTile(tile2).withA(king).onTile(kingTile).build();
        Assertions.assertEquals(expected, moveGenerator.getAttackersOnKingOfColor(board, color).size());
        return board;
    }
    private void assertEquals(List<Move> expected, List<Move> actual){
        expected = new ArrayList<>(expected);
        actual = new ArrayList<>(actual);
        Collections.sort(expected);
        Collections.sort(actual);
        try{
            Assertions.assertEquals(expected, actual);
        } catch(Error e){
            HashSet<Move> expectedMoveSet = new HashSet<>(expected);
            HashSet<Move> actualMoveSet = new HashSet<>(actual);
            System.out.println("Missing Moves: ");
            expected.stream().filter(expectedMove -> !actualMoveSet.contains(expectedMove)).forEach(System.out::println);
            System.out.println("Extra Moves: ");
            actual.stream().filter(actualMove -> !expectedMoveSet.contains(actualMove)).forEach(System.out::println);
            throw new AssertionFailedError("Assertion failed!");

        }
    }
}
