package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;

class MoveGeneratorServiceImplTest {

    private MoveGeneratorServiceImpl moveGeneratorService;
    @BeforeEach
    public void setUp(){
        moveGeneratorService = new MoveGeneratorServiceImpl();
    }
    @Test
    public void GenerateKnightMoves_GivenKnightOnEmptyBoard_ReturnMoves(){
        BoardBuilder boardBuilder = BoardBuilder.getInstance();
    }

    private BoardBuilder boardWithA(Piece piece){
        return BoardBuilder.getInstance().setPiece(piece);
    }
    private BoardBuilder onTile(int tile){
        return BoardBuilder.getInstance().setTile(tile);
    }
    private static class BoardBuilder{
        private Piece piece;
        private int tile;
        private static BoardBuilder boardBuilder = null;
        private HashMap<Integer, Piece> map;

        private BoardBuilder() {}
        public static BoardBuilder getInstance(){
            if(boardBuilder == null){
                boardBuilder = new BoardBuilder();
            }
            return boardBuilder;
        }
        public BoardBuilder setPiece(Piece piece){
            this.piece = piece;
            return this;
        }
        public BoardBuilder setTile(int tile){
            this.tile = tile;
            return this;
        }
        public BoardBuilder add(){
            map.put(tile, piece);
            return this;
        }
        public Board build() {
            return new Board(map);
        }
        public BoardBuilder clear(){
            map = new HashMap<>();
            return this;
        }
    }
}
