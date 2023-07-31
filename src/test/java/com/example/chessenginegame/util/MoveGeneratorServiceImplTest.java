package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.Pin;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;
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
    public void GeneratePawnMoves_GivenPawnOnEmptyBoard_ReturnMoves(){
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
    public void GenerateKnightMoves_GivenKnightOnEmptyBoard_ReturnMoves(){
        Piece knight = Piece.of('N');
        testSinglePiece(knight, 35, 18, 20, 25, 29, 41, 45, 50, 52);
        testSinglePiece(knight, 49, 32, 34, 43, 59);
        testSinglePiece(knight, 1, 11, 16, 18);
        testSinglePiece(knight, 7, 13, 22);
        testSinglePiece(knight, 39, 22, 29, 45, 54);
        testSinglePiece(knight, 24, 9, 18, 34, 41);
        testSinglePiece(knight, 61, 44, 46, 51, 55);
    }
    public static void main(String[] args){
        MoveGeneratorServiceImplTest tests = new MoveGeneratorServiceImplTest();
        tests.GenerateKnightMoves_GivenKnightOnEmptyBoard_ReturnMoves();
        tests.GeneratePawnMoves_GivenPawnOnEmptyBoard_ReturnMoves();
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
        Collections.sort(new ArrayList<>(expected));
        Collections.sort(new ArrayList<>(actual));
        Assertions.assertEquals(expected, actual);
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
