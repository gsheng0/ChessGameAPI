package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveGeneratorServiceImplTest {
    private MoveGeneratorServiceImpl moveGeneratorService;
    private BoardBuilder boardBuilder;
    private PieceBuilder pieceBuilder;
    @BeforeEach
    public void setUp(){
        moveGeneratorService = new MoveGeneratorServiceImpl();
        boardBuilder = BoardBuilder.getInstance();
        pieceBuilder = PieceBuilder.getInstance();
    }
    public MoveGeneratorServiceImplTest(){
        moveGeneratorService = new MoveGeneratorServiceImpl();
        boardBuilder = BoardBuilder.getInstance();
        pieceBuilder = PieceBuilder.getInstance();
    }
    @Test
    public void PawnOnEmptyBoard(){
        Piece whitePawn = Piece.of('P');
        testSinglePieceMoveGeneration(whitePawn, 51, 43, 35);
        testSinglePieceMoveGeneration(whitePawn, 32, 24);
        testSinglePieceMoveGeneration(whitePawn, 55, 47, 39);
        testSinglePieceMoveGeneration(whitePawn, 35, 27);

        Piece blackPawn = Piece.of('p');
        testSinglePieceMoveGeneration(blackPawn, 8, 16, 24);
        testSinglePieceMoveGeneration(blackPawn, 15, 23, 31);
        testSinglePieceMoveGeneration(blackPawn, 20, 28);
        testSinglePieceMoveGeneration(blackPawn, 35, 43);

    }
    @Test
    public void KnightOnEmptyBoard(){
        Piece whiteKnight = Piece.of('N');
        testSinglePieceMoveGeneration(whiteKnight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
        testSinglePieceMoveGeneration(whiteKnight, 49, 32, 34, 43, 59);
        testSinglePieceMoveGeneration(whiteKnight, 1, 11, 16, 18);
        testSinglePieceMoveGeneration(whiteKnight, 7, 13, 22);
        testSinglePieceMoveGeneration(whiteKnight, 39, 22, 29, 45, 54);
        testSinglePieceMoveGeneration(whiteKnight, 24, 9, 18, 34, 41);
        testSinglePieceMoveGeneration(whiteKnight, 61, 44, 46, 51, 55);

        Piece blackKnight = Piece.of('n');
        testSinglePieceMoveGeneration(blackKnight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
        testSinglePieceMoveGeneration(blackKnight, 49, 32, 34, 43, 59);
        testSinglePieceMoveGeneration(blackKnight, 1, 11, 16, 18);
        testSinglePieceMoveGeneration(blackKnight, 7, 13, 22);
        testSinglePieceMoveGeneration(blackKnight, 39, 22, 29, 45, 54);
        testSinglePieceMoveGeneration(blackKnight, 24, 9, 18, 34, 41);
        testSinglePieceMoveGeneration(blackKnight, 61, 44, 46, 51, 55);
    }
    @Test
    public void BishopOnEmptyBoard(){
        Piece whiteBishop = Piece.of('B');
        testSinglePieceMoveGeneration(whiteBishop, 56, 49, 42, 35, 28, 21, 14, 7);
        testSinglePieceMoveGeneration(whiteBishop, 63, 54, 45, 36, 27, 18, 9, 0);
        testSinglePieceMoveGeneration(whiteBishop, 35, 56, 49, 42, 28, 21, 14, 7, 8, 17, 26, 44, 53, 62);
        testSinglePieceMoveGeneration(whiteBishop, 36, 57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testSinglePieceMoveGeneration(whiteBishop, 10, 1, 19, 28, 37, 46, 55, 3, 17, 24);

        Piece blackBishop = Piece.of('b');
        testSinglePieceMoveGeneration(blackBishop, 56, 49, 42, 35, 28, 21, 14, 7);
        testSinglePieceMoveGeneration(blackBishop, 63, 54, 45, 36, 27, 18, 9, 0);
        testSinglePieceMoveGeneration(blackBishop, 35, 56, 49, 42, 28, 21, 14, 7, 8, 17, 26, 44, 53, 62);
        testSinglePieceMoveGeneration(blackBishop, 36, 57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testSinglePieceMoveGeneration(blackBishop, 10, 1, 19, 28, 37, 46, 55, 3, 17, 24);
    }
    @Test
    public void RookOnEmptyBoard(){
        Piece whiteRook = Piece.of('R');
        testSinglePieceMoveGeneration(whiteRook, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63);
        testSinglePieceMoveGeneration(whiteRook, 7, 6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63);
        testSinglePieceMoveGeneration(whiteRook, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39);
        testSinglePieceMoveGeneration(whiteRook, 25, 24, 26, 27, 28, 29, 30, 31, 1, 9, 17, 33, 41, 49, 57);
        testSinglePieceMoveGeneration(whiteRook, 54, 6, 14, 22, 30, 38, 46, 62, 48, 49, 50, 51, 52, 53, 55);
        testSinglePieceMoveGeneration(whiteRook, 1, 0, 2, 3, 4, 5, 6, 7, 9, 17, 25, 33, 41, 49, 57);

        Piece blackRook = Piece.of('r');
        testSinglePieceMoveGeneration(blackRook, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63);
        testSinglePieceMoveGeneration(blackRook, 7, 6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63);
        testSinglePieceMoveGeneration(blackRook, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39);
        testSinglePieceMoveGeneration(blackRook, 25, 24, 26, 27, 28, 29, 30, 31, 1, 9, 17, 33, 41, 49, 57);
        testSinglePieceMoveGeneration(blackRook, 54, 6, 14, 22, 30, 38, 46, 62, 48, 49, 50, 51, 52, 53, 55);
        testSinglePieceMoveGeneration(blackRook, 1, 0, 2, 3, 4, 5, 6, 7, 9, 17, 25, 33, 41, 49, 57);
    }
    @Test
    public void QueenOnEmptyBoard(){
        Piece whiteQueen = Piece.of('Q');
        testSinglePieceMoveGeneration(whiteQueen, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63,
                49, 42, 35, 28, 21, 14, 7);
        testSinglePieceMoveGeneration(whiteQueen, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39,
                57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testSinglePieceMoveGeneration(whiteQueen, 62, 6, 14, 22, 30, 38, 46, 54, 56, 57, 58, 59, 60, 61, 63,
                53, 44, 35, 26, 17, 8, 55);
        testSinglePieceMoveGeneration(whiteQueen, 11, 3, 19, 27, 35, 43, 51, 59, 8, 9, 10, 12, 13, 14, 15,
                2, 20, 29, 38, 47, 4, 18, 25, 32);
        testSinglePieceMoveGeneration(whiteQueen, 58, 50, 42, 34, 26, 18, 10, 2, 56, 57, 59, 60, 61, 62, 63,
                40, 49, 23, 30, 37, 44, 51);

        Piece blackQueen = Piece.of('q');
        testSinglePieceMoveGeneration(blackQueen, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63,
                49, 42, 35, 28, 21, 14, 7);
        testSinglePieceMoveGeneration(blackQueen, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39,
                57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testSinglePieceMoveGeneration(blackQueen, 62, 6, 14, 22, 30, 38, 46, 54, 56, 57, 58, 59, 60, 61, 63,
                53, 44, 35, 26, 17, 8, 55);
        testSinglePieceMoveGeneration(blackQueen, 11, 3, 19, 27, 35, 43, 51, 59, 8, 9, 10, 12, 13, 14, 15,
                2, 20, 29, 38, 47, 4, 18, 25, 32);
        testSinglePieceMoveGeneration(blackQueen, 58, 50, 42, 34, 26, 18, 10, 2, 56, 57, 59, 60, 61, 62, 63,
                40, 49, 23, 30, 37, 44, 51);
    }
    @Test
    public void KingOnEmptyBoard(){
        Piece whiteKing = Piece.of('K');
        testSinglePieceMoveGeneration(whiteKing, 60, 51, 52, 53, 59, 61);
        testSinglePieceMoveGeneration(whiteKing, 27, 18, 19, 20, 26, 28, 34, 35, 36);
        testSinglePieceMoveGeneration(whiteKing, 39, 30, 31, 38, 46, 47);
        testSinglePieceMoveGeneration(whiteKing, 40, 32, 33, 41, 48, 49);

        Piece blackKing = Piece.of('k');
        testSinglePieceMoveGeneration(blackKing, 60, 51, 52, 53, 59, 61);
        testSinglePieceMoveGeneration(blackKing, 27, 18, 19, 20, 26, 28, 34, 35, 36);
        testSinglePieceMoveGeneration(blackKing, 39, 30, 31, 38, 46, 47);
        testSinglePieceMoveGeneration(blackKing, 40, 32, 33, 41, 48, 49);
    }
    @Test
    public void NoChecks(){
        testGetAttackerCount(Constants.WHITE, 60, "r", 51, 0);
        testGetAttackerCount(Constants.WHITE, 60, "n", 51, 0);
        testGetAttackerCount(Constants.WHITE, 43, "b", 35, 0);
        testGetAttackerCount(Constants.WHITE, 43, "p", 35, 0);
        testGetAttackerCount(Constants.WHITE, 43, "q", 43, 0);
        testGetAttackerCount(Constants.WHITE, 9, "q", 3, 0);
        testGetAttackerCount(Constants.WHITE, 6, "b", 14, 0);
        testGetAttackerCount(Constants.WHITE, 15, "r", 20, 0);
        testGetAttackerCount(Constants.WHITE, 19, "p", 18, 0);
        testGetAttackerCount(Constants.WHITE, 39, "n", 24, 0);
        testGetAttackerCount(Constants.WHITE, 32, "p", 23, 0);
        testGetAttackerCount(Constants.WHITE, 48, "b", 39, 0);
        testGetAttackerCount(Constants.WHITE, 48, "p", 39, 0);
        testGetAttackerCount(Constants.WHITE, 8, "q", 25, 0);
        testGetAttackerCount(Constants.WHITE, 5, "p", 14, 0);
        testGetAttackerCount(Constants.WHITE, 0, "b", 7, 0);

        testGetAttackerCount(Constants.BLACK, 60, "R", 51, 0);
        testGetAttackerCount(Constants.BLACK, 60, "N", 51, 0);
        testGetAttackerCount(Constants.BLACK, 43, "B", 35, 0);
        testGetAttackerCount(Constants.BLACK, 43, "P", 35, 0);
        testGetAttackerCount(Constants.BLACK, 43, "Q", 43, 0);
        testGetAttackerCount(Constants.BLACK, 9, "Q", 3, 0);
        testGetAttackerCount(Constants.BLACK, 6, "B", 14, 0);
        testGetAttackerCount(Constants.BLACK, 15, "R", 20, 0);
        testGetAttackerCount(Constants.BLACK, 19, "P", 18, 0);
        testGetAttackerCount(Constants.BLACK, 39, "N", 24, 0);
        testGetAttackerCount(Constants.BLACK, 32, "P", 23, 0);
        testGetAttackerCount(Constants.BLACK, 48, "B", 39, 0);
        testGetAttackerCount(Constants.BLACK, 48, "P", 39, 0);
        testGetAttackerCount(Constants.BLACK, 8, "Q", 25, 0);
        testGetAttackerCount(Constants.BLACK, 61, "P", 54, 0);
        testGetAttackerCount(Constants.BLACK, 0, "B", 7, 0);
    }
    @Test
    public void SinglePieceChecks(){
        testGetAttackerCount(Constants.WHITE, 60, "r", 56, 1);
        testGetAttackerCount(Constants.WHITE, 10, "b", 55, 1);
        testGetAttackerCount(Constants.WHITE, 34, "n", 44, 1);
        testGetAttackerCount(Constants.WHITE, 54, "p", 45, 1);
        testGetAttackerCount(Constants.WHITE, 35, "p", 28, 1);
        testGetAttackerCount(Constants.WHITE, 18, "p", 11, 1);
        testGetAttackerCount(Constants.WHITE, 4, "q", 32, 1);
        testGetAttackerCount(Constants.WHITE, 9, "r", 57, 1);
        testGetAttackerCount(Constants.WHITE, 7, "b", 56, 1);
        testGetAttackerCount(Constants.WHITE, 32, "r", 39, 1);

        testGetAttackerCount(Constants.BLACK, 60, "R", 56, 1);
        testGetAttackerCount(Constants.BLACK, 10, "B", 55, 1);
        testGetAttackerCount(Constants.BLACK, 34, "N", 44, 1);
        testGetAttackerCount(Constants.BLACK, 5, "P", 14, 1);
        testGetAttackerCount(Constants.BLACK, 28, "P", 35, 1);
        testGetAttackerCount(Constants.BLACK, 11, "P", 18, 1);
        testGetAttackerCount(Constants.BLACK, 4, "Q", 32, 1);
        testGetAttackerCount(Constants.BLACK, 9, "R", 57, 1);
        testGetAttackerCount(Constants.BLACK, 7, "B", 56, 1);
        testGetAttackerCount(Constants.BLACK, 32, "R", 39, 1);
    }
    @Test
    public void DoublePieceChecks(){
        testGetAttackerCount(Constants.WHITE, 27, "p", 18, "p", 20, 2);
        testGetAttackerCount(Constants.WHITE, 2, "n", 17, "n", 19, 2);
        testGetAttackerCount(Constants.WHITE, 30, "b", 23, "b", 51, 2);
        testGetAttackerCount(Constants.WHITE, 53, "r", 5, "r", 48, 2);
        testGetAttackerCount(Constants.WHITE, 57, "q", 15, "q", 25, 2);
        testGetAttackerCount(Constants.WHITE, 62, "p", 55, "r", 14, 2);
        testGetAttackerCount(Constants.WHITE, 16, "n", 26, "b", 52, 2);
        testGetAttackerCount(Constants.WHITE, 25, "q", 4, "n", 8, 2);
        testGetAttackerCount(Constants.WHITE, 41, "b", 13, "r", 46, 2);
        testGetAttackerCount(Constants.WHITE, 39, "n", 22, "q", 33, 2);

        testGetAttackerCount(Constants.BLACK, 27, "P", 34, "P", 36, 2);
        testGetAttackerCount(Constants.BLACK, 2, "N", 17, "N", 19, 2);
        testGetAttackerCount(Constants.BLACK, 30, "B", 23, "B", 51, 2);
        testGetAttackerCount(Constants.BLACK, 53, "R", 5, "R", 48, 2);
        testGetAttackerCount(Constants.BLACK, 57, "Q", 15, "Q", 25, 2);
        testGetAttackerCount(Constants.BLACK, 46, "P", 55, "R", 14, 2);
        testGetAttackerCount(Constants.BLACK, 16, "N", 26, "B", 52, 2);
        testGetAttackerCount(Constants.BLACK, 25, "Q", 4, "N", 8, 2);
        testGetAttackerCount(Constants.BLACK, 41, "B", 13, "R", 46, 2);
        testGetAttackerCount(Constants.BLACK, 39, "N", 22, "Q", 33, 2);
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
    public void PinnedPiece(){
        testPinnedPieceMoveGeneration(60, "R", 53, "b", 39);
        testPinnedPieceMoveGeneration(60, "B", 53, "b", 39, 46, 39);
        testPinnedPieceMoveGeneration(60, "R", 36, "r", 12, 44, 52, 28, 20, 12);
        testPinnedPieceMoveGeneration(60, "P", 52, "r", 4, 44, 36);
        testPinnedPieceMoveGeneration(60, "B", 52, "q", 20);

        testPinnedPieceMoveGeneration(60, "r", 53, "B", 39);
        testPinnedPieceMoveGeneration(60, "b", 53, "B", 39, 46, 39);
        testPinnedPieceMoveGeneration(60, "r", 36, "R", 12, 44, 52, 28, 20, 12);
        testPinnedPieceMoveGeneration(60, "p", 52, "R", 4, 44, 36);
        testPinnedPieceMoveGeneration(60, "b", 52, "Q", 20);
    }
//    public static void main(String[] args){
//        MoveGeneratorServiceImplTest tests = new MoveGeneratorServiceImplTest();
//        tests.KnightOnEmptyBoard();
//        tests.PawnOnEmptyBoard();
//        tests.BishopOnEmptyBoard();
//        tests.RookOnEmptyBoard();
//        tests.QueenOnEmptyBoard();
//        tests.KingOnEmptyBoard();
//        tests.NoChecks();
//        tests.SinglePieceChecks();
//        tests.DoublePieceChecks();
//        tests.BlockedSingleChecks();
//        tests.PinnedPiece();
//    }
    private void testPinnedPieceMoveGeneration(int kingTile, String pinned, int pinnedTile, String pinning, int pinningTile, int... expected){
        Piece pinnedPiece = Piece.of(pinned);
        Piece pinningPiece = Piece.of(pinning);
        King king = new King(pinnedPiece.getColor());
        Board board = boardBuilder.newBoard().withA(pinnedPiece).onTile(pinnedTile).withA(pinningPiece).onTile(pinningTile).withA(king).onTile(kingTile).build();
        List<Move> expectedMoves = createMoveList(pinnedPiece, pinnedTile, Arrays.stream(expected).boxed().toList());
        List<Move> generatedMoves = moveGeneratorService.generateLegalMoves(board, pinnedPiece.getColor()).stream().filter(move -> move.getPiece().equals(pinnedPiece)).toList();
        assertEquals(expectedMoves, generatedMoves);
    }
    private void testSinglePieceMoveGeneration(Piece piece, int tile, int... expected){
        Board board = boardBuilder.newBoard().withA(piece).onTile(tile).build();
        List<Move> expectedMoves = createMoveList(piece, tile, Arrays.stream(expected).boxed().toList());
        if(piece instanceof Pawn pawn){
            assertEquals(expectedMoves, moveGeneratorService.generatePawnMoves(pawn, tile, board, null));
        } else if(piece instanceof Knight){
            assertEquals(expectedMoves, moveGeneratorService.generateKnightMoves((Knight)piece, tile, board, null));
        } else if(piece instanceof SlidingPiece slidingPiece){
            assertEquals(expectedMoves, moveGeneratorService.generateSlidingPieceMoves(slidingPiece, tile, board, null));
        } else if(piece instanceof King king) {
            assertEquals(expectedMoves, moveGeneratorService.generateKingMoves1(king, tile, board));
        }
    }
    private void testGetAttackerCount(String color, int kingTile, String p, int tile, int expected){
        Piece piece = Piece.of(p);
        King king = new King(color);
        Board board = boardBuilder.newBoard().withA(piece).onTile(tile).withA(king).onTile(kingTile).build();
        Assertions.assertEquals(expected, moveGeneratorService.getAttackersOnKingOfColor(board, color).size());
    }
    private void testGetAttackerCount(String color, int kingTile, String p1, int tile1, String p2, int tile2, int expected){
        King king = new King(color);
        Piece piece1 = Piece.of(p1);
        Piece piece2 = Piece.of(p2);
        Board board = boardBuilder.newBoard().withA(piece1).onTile(tile1).withA(piece2).onTile(tile2).withA(king).onTile(kingTile).build();
        Assertions.assertEquals(expected, moveGeneratorService.getAttackersOnKingOfColor(board, color).size());
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
    private List<Move> createMoveList(Piece piece, int startTile, List<Integer> endTiles){
        return endTiles.stream().map(endTile -> new Move(piece, startTile, endTile)).toList();
    }
    private static class BoardBuilder{
        private Piece piece = null;
        private int tile = -1;
        private static BoardBuilder boardBuilder = null;
        private HashMap<Integer, Piece> map;

        private BoardBuilder() {}
        public static BoardBuilder getInstance(){
            if(boardBuilder == null){
                boardBuilder = new BoardBuilder();
            }
            return boardBuilder;
        }
        public BoardBuilder newBoard(){
            map = new HashMap<>();
            return this;
        }
        public BoardBuilder withA(Piece piece){
            this.piece = piece;
            if(tile != -1){
                add();
            }
            return this;
        }
        public BoardBuilder onTile(int tile){
            this.tile = tile;
            if(piece != null){
                add();
            }
            return this;
        }
        public BoardBuilder add(){
            map.put(tile, piece);
            tile = -1;
            piece = null;
            return this;
        }
        public Board build(){
            return new Board(map);
        }

    }
    private static class PieceBuilder{
        private String color = null;
        private static PieceBuilder pieceBuilder = null;
        private PieceBuilder() {}
        public static PieceBuilder getInstance(){
            if(pieceBuilder == null){
                pieceBuilder = new PieceBuilder();
            }
            return pieceBuilder;
        }
        public PieceBuilder black(){
            this.color = Constants.BLACK;
            return this;
        }
        public PieceBuilder white(){
            this.color = Constants.WHITE;
            return this;
        }
        public Piece pawn(){
            if(this.color == null){
                white();
            }
            return new Pawn(color);
        }
        public Piece knight(){
            if(this.color == null){
                white();
            }
            return new Knight(color);
        }
        public Piece bishop(){
            if(this.color == null){
                white();
            }
            return new Bishop(color);
        }
        public Piece rook(){
            if(this.color == null){
                white();
            }
            return new Rook(color);
        }
        public Piece queen(){
            if(this.color == null){
                white();
            }
            return new Queen(color);
        }
        public Piece king(){
            if(this.color == null){
                white();
            }
            return new King(color);
        }
    }
}
