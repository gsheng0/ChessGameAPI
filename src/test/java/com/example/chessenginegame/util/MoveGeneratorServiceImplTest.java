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
        testGenerateMoves(whitePawn, 51, 43, 35);
        testGenerateMoves(whitePawn, 32, 24);
        testGenerateMoves(whitePawn, 55, 47, 39);
        testGenerateMoves(whitePawn, 35, 27);

        Piece blackPawn = Piece.of('p');
        testGenerateMoves(blackPawn, 8, 16, 24);
        testGenerateMoves(blackPawn, 15, 23, 31);
        testGenerateMoves(blackPawn, 20, 28);
        testGenerateMoves(blackPawn, 35, 43);

    }
    @Test
    public void KnightOnEmptyBoard(){
        Piece knight = Piece.of('N');
        testGenerateMoves(knight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
        testGenerateMoves(knight, 49, 32, 34, 43, 59);
        testGenerateMoves(knight, 1, 11, 16, 18);
        testGenerateMoves(knight, 7, 13, 22);
        testGenerateMoves(knight, 39, 22, 29, 45, 54);
        testGenerateMoves(knight, 24, 9, 18, 34, 41);
        testGenerateMoves(knight, 61, 44, 46, 51, 55);
    }
    @Test
    public void BishopOnEmptyBoard(){
        Piece bishop = Piece.of('B');
        testGenerateMoves(bishop, 56, 49, 42, 35, 28, 21, 14, 7);
        testGenerateMoves(bishop, 63, 54, 45, 36, 27, 18, 9, 0);
        testGenerateMoves(bishop, 35, 56, 49, 42, 28, 21, 14, 7, 8, 17, 26, 44, 53, 62);
        testGenerateMoves(bishop, 36, 57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testGenerateMoves(bishop, 10, 1, 19, 28, 37, 46, 55, 3, 17, 24);
    }
    @Test
    public void RookOnEmptyBoard(){
        Piece rook = Piece.of('R');
        testGenerateMoves(rook, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63);
        testGenerateMoves(rook, 7, 6, 5, 4, 3, 2, 1, 0, 15, 23, 31, 39, 47, 55, 63);
        testGenerateMoves(rook, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39);
        testGenerateMoves(rook, 25, 24, 26, 27, 28, 29, 30, 31, 1, 9, 17, 33, 41, 49, 57);
        testGenerateMoves(rook, 54, 6, 14, 22, 30, 38, 46, 62, 48, 49, 50, 51, 52, 53, 55);
        testGenerateMoves(rook, 1, 0, 2, 3, 4, 5, 6, 7, 9, 17, 25, 33, 41, 49, 57);
    }
    @Test
    public void QueenOnEmptyBoard(){
        Piece queen = Piece.of('Q');
        testGenerateMoves(queen, 56, 48, 40, 32, 24, 16, 8, 0, 57, 58, 59, 60, 61, 62, 63,
                49, 42, 35, 28, 21, 14, 7);
        testGenerateMoves(queen, 36, 28, 20, 12, 4, 44, 52, 60, 32, 33, 34, 35, 37, 38, 39,
                57, 50, 43, 29, 22, 15, 0, 9, 18, 27, 45, 54, 63);
        testGenerateMoves(queen, 62, 6, 14, 22, 30, 38, 46, 54, 56, 57, 58, 59, 60, 61, 63,
                53, 44, 35, 26, 17, 8, 55);
        testGenerateMoves(queen, 11, 3, 19, 27, 35, 43, 51, 59, 8, 9, 10, 12, 13, 14, 15,
                2, 20, 29, 38, 47, 4, 18, 25, 32);
        testGenerateMoves(queen, 58, 50, 42, 34, 26, 18, 10, 2, 56, 57, 59, 60, 61, 62, 63,
                40, 49, 23, 30, 37, 44, 51);
    }
    @Test
    public void KingOnEmptyBoard(){
        Piece king = Piece.of('K');
        testGenerateMoves(king, 60, 51, 52, 53, 59, 61);
        testGenerateMoves(king, 27, 18, 19, 20, 26, 28, 34, 35, 36);
        testGenerateMoves(king, 39, 30, 31, 38, 46, 47);
        testGenerateMoves(king, 40, 32, 33, 41, 48, 49);
    }
    @Test
    public void NoChecks(){
        testGetAttackerCount(60, "r", 51 , 0);
        testGetAttackerCount(60, "n", 51, 0);
        testGetAttackerCount(43, "b", 35, 0);
        testGetAttackerCount(43, "p", 35, 0);
        testGetAttackerCount(43, "q", 43, 0);
        testGetAttackerCount(9, "q", 3, 0);
        testGetAttackerCount(6, "b", 14, 0);
        testGetAttackerCount(15, "r", 20, 0);
        testGetAttackerCount(19, "p", 18, 0);
        testGetAttackerCount(39, "n", 24, 0);
        testGetAttackerCount(32, "p", 23, 0);
        testGetAttackerCount(48, "b", 39, 0);
        testGetAttackerCount(48, "p", 39, 0);
        testGetAttackerCount(8, "q", 25, 0);
        testGetAttackerCount(5, "p", 14, 0);
        testGetAttackerCount(0, "b", 7, 0);
    }
    @Test
    public void SinglePieceChecks(){
        testGetAttackerCount(60, "r", 56, 1);
        testGetAttackerCount(10, "b", 55, 1);
        testGetAttackerCount(34, "n", 44, 1);
        testGetAttackerCount(54, "p", 45, 1);
        testGetAttackerCount(35, "p", 28, 1);
        testGetAttackerCount(18, "p", 11, 1);
        testGetAttackerCount(4, "q", 32, 1);
        testGetAttackerCount(9, "r", 57, 1);
        testGetAttackerCount(7, "b", 56, 1);
        testGetAttackerCount(32, "r", 39, 1);
    }
    @Test
    public void DoublePieceChecks(){
        testGetAttackerCount(27, "p", 18, "p", 20, 2);
        testGetAttackerCount(2, "n", 17, "n", 19, 2);
        testGetAttackerCount(30, "b", 23, "b", 51, 2);
        testGetAttackerCount(53, "r", 5, "r", 48, 2);
        testGetAttackerCount(57, "q", 15, "q", 25, 2);
        testGetAttackerCount(62, "p", 55, "r", 14, 2);
        testGetAttackerCount(16, "n", 26, "b", 52, 2);
        testGetAttackerCount(25, "q", 4, "n", 8, 2);
        testGetAttackerCount(41, "b", 13, "r", 46, 2);
        testGetAttackerCount(39, "n", 22, "q", 33, 2);
    }
    @Test
    public void BlockedSingleChecks(){
        testGetAttackerCount(60, "r", 56, "p", 57, 0);
        testGetAttackerCount(10, "b", 55, "n", 37, 0);
        testGetAttackerCount(26, "q", 12, "r", 19, 0);
        testGetAttackerCount(4, "q", 32, "n", 18, 0);
        testGetAttackerCount(9, "r", 57, "b", 33, 0);
        testGetAttackerCount(7, "b", 56, "r", 42, 0);
        testGetAttackerCount(32, "r", 39, "n", 38, 0);
    }
    public static void main(String[] args){
        MoveGeneratorServiceImplTest tests = new MoveGeneratorServiceImplTest();
        tests.KnightOnEmptyBoard();
        tests.PawnOnEmptyBoard();
        tests.BishopOnEmptyBoard();
        tests.RookOnEmptyBoard();
        tests.QueenOnEmptyBoard();
        tests.KingOnEmptyBoard();
        tests.NoChecks();
        tests.SinglePieceChecks();
        tests.DoublePieceChecks();
        tests.BlockedSingleChecks();

    }

    private void testGenerateMoves(Piece piece, int tile, int... expected){
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
    private void testGetAttackerCount(int kingTile, String p, int tile, int expected){
        Piece piece = Piece.of(p);
        Board board = boardBuilder.newBoard().withA(piece).onTile(tile).withA(Piece.of('K')).onTile(kingTile).build();
        Assertions.assertEquals(expected, moveGeneratorService.getAttackersOnKingOfColor(board, Constants.WHITE).size());
    }
    private void testGetAttackerCount(int kingTile, String p1, int tile1, String p2, int tile2, int expected){
        Piece piece1 = Piece.of(p1);
        Piece piece2 = Piece.of(p2);
        Board board = boardBuilder.newBoard().withA(piece1).onTile(tile1).withA(piece2).onTile(tile2).withA(Piece.of('K')).onTile(kingTile).build();
        Assertions.assertEquals(expected, moveGeneratorService.getAttackersOnKingOfColor(board, Constants.WHITE).size());
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
