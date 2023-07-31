package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;
import junit.framework.AssertionFailedError;
import org.junit.Test;
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
        testSinglePiece(whitePawn, 51, 43, 35);
        testSinglePiece(whitePawn, 32, 24);
        testSinglePiece(whitePawn, 55, 47, 39);
        testSinglePiece(whitePawn, 35, 27);

        Piece blackPawn = Piece.of('p');
        testSinglePiece(blackPawn, 8, 16, 24);
        testSinglePiece(blackPawn, 15, 23, 31);
        testSinglePiece(blackPawn, 20, 28);
        testSinglePiece(blackPawn, 35, 43);

    }
    @Test
    public void KnightOnEmptyBoard(){
        Piece knight = Piece.of('N');
        testSinglePiece(knight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
        testSinglePiece(knight, 49, 32, 34, 43, 59);
        testSinglePiece(knight, 1, 11, 16, 18);
        testSinglePiece(knight, 7, 13, 22);
        testSinglePiece(knight, 39, 22, 29, 45, 54);
        testSinglePiece(knight, 24, 9, 18, 34, 41);
        testSinglePiece(knight, 61, 44, 46, 51, 55);
    }
    @Test
    public void BishopOnEmptyBoard(){
        Piece bishop = Piece.of('B');
        testSinglePiece(bishop, 56, 49, 42, 35, 28, 21, 14, 7);
        testSinglePiece(bishop, 63, 54, 45, 36, 27, 18, 9, 0);
        testSinglePiece(bishop, 35, 56, 49, 42, 28, 21, 14, 7, 8, 17, 26, 44, 53, 62);
        testSinglePiece(bishop, 36, 57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testSinglePiece(bishop, 10, 1, 19, 28, 37, 46, 55, 3, 17, 24);
    }
    @Test
    public void RookOnEmptyBoard(){
        Piece rook = Piece.of('R');
        testSinglePiece(rook, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63);
        testSinglePiece(rook, 7, 6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63);
        testSinglePiece(rook, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39);
        testSinglePiece(rook, 25, 24, 26, 27, 28, 29, 30, 31, 1, 9, 17, 33, 41, 49, 57);
        testSinglePiece(rook, 54, 6, 14, 22, 30, 38, 46, 62, 48, 49, 50, 51, 52, 53, 55);
        testSinglePiece(rook, 1, 0, 2, 3, 4, 5, 6, 7, 9, 17, 25, 33, 41, 49, 57);
    }
    @Test
    public void QueenOnEmptyBoard(){
        Piece queen = Piece.of('Q');
        testSinglePiece(queen, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63,
                49, 42, 35, 28, 21, 14, 7);
        testSinglePiece(queen, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39,
                57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testSinglePiece(queen, 62, 6, 14, 22, 30, 38, 46, 54, 56, 57, 58, 59, 60, 61, 63,
                53, 44, 35, 26, 17, 8, 55);
        testSinglePiece(queen, 11, 3, 19, 27, 35, 43, 51, 59, 8, 9, 10, 12, 13, 14, 15,
                2, 20, 29, 38, 47, 4, 18, 25, 32);
        testSinglePiece(queen, 58, 50, 42, 34, 26, 18, 10, 2, 56, 57, 59, 60, 61, 62, 63,
                40, 49, 23, 30, 37, 44, 51);
    }
    @Test
    public void KingOnEmptyBoard(){
        Piece king = Piece.of('K');
        testSinglePiece(king, 60, 51, 52, 53, 59, 61);
        testSinglePiece(king, 27, 18, 19, 20, 26, 28, 34, 35, 36);
        testSinglePiece(king, 39, 30, 31, 38, 46, 47);
        testSinglePiece(king, 40, 32, 33, 41, 48, 49);
    }
    public static void main(String[] args){
        MoveGeneratorServiceImplTest tests = new MoveGeneratorServiceImplTest();
        tests.KnightOnEmptyBoard();
        tests.PawnOnEmptyBoard();
        tests.BishopOnEmptyBoard();
        tests.RookOnEmptyBoard();
        tests.QueenOnEmptyBoard();
        tests.KingOnEmptyBoard();
    }

    private void testSinglePiece(Piece piece, int tile, int... expected){
        Board board = boardBuilder.newBoard().withA(piece).onTile(tile).build();
        List<Move> expectedMoves = createMoveList(piece, tile, Arrays.stream(expected).boxed().toList());
        if(piece instanceof Pawn pawn){
            assertEquals(expectedMoves, moveGeneratorService.generatePawnMoves(pawn, tile, board, null));
        } else if(piece instanceof Knight){
            assertEquals(expectedMoves, moveGeneratorService.generateKnightMoves(piece, tile, board, null));
        } else if(piece instanceof SlidingPiece slidingPiece){
            assertEquals(expectedMoves, moveGeneratorService.generateSlidingPieceMoves(slidingPiece, tile, board, null));
        } else if(piece instanceof King king) {
            assertEquals(expectedMoves, moveGeneratorService.generateKingMoves1(king, tile, board));
        }
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
//            for(int i = 0; i < expected.size(); i++){
//                System.out.println(expected.get(i) + " " + actual.get(i));
//            }
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
    private Board boardWith(Class<? extends Piece> type, int tile){
        if(type.equals(Pawn.class)){
            return boardBuilder.newBoard()
                    .withA(pieceBuilder.pawn()).onTile(tile).build();
        } else if(type.equals(Knight.class)){
            return boardBuilder.newBoard()
                    .withA(pieceBuilder.knight()).onTile(tile).build();
        } else if(type.equals(Bishop.class)){
            return boardBuilder.newBoard()
                    .withA(pieceBuilder.bishop()).onTile(tile).build();
        } else if(type.equals(Rook.class)){
            return boardBuilder.newBoard()
                    .withA(pieceBuilder.rook()).onTile(tile).build();
        } else if(type.equals(Queen.class)){
            return boardBuilder.newBoard()
                    .withA(pieceBuilder.queen()).onTile(tile).build();
        } else if(type.equals(King.class)){
            return boardBuilder.newBoard()
                    .withA(pieceBuilder.king()).onTile(tile).build();
        }
        return null;
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
