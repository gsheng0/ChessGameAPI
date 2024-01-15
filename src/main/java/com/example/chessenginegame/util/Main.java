package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static MoveGeneratorService moveGenerator = new MoveGeneratorServiceImpl();
    public static void main(String[] args){
        Window window = new Window();
        List<Board> boards = ChessGameTester.generateMoves(Board.startingPosition(), 0, 2);
        window.setBoards(boards);
        window.show();
    }
    public static List<Board> generateRandomGame(int moves){
        List<Board> game = new ArrayList<>();
        Board board = Board.startingPosition();
        game.add(board);
        for(int i = 0; i < moves; i++){
            String color = i % 2 == 0 ? Constants.WHITE : Constants.BLACK;
            List<Move> legalMoves = moveGenerator.generateLegalMoves(board, color);
            if(legalMoves.size() == 0){
                break;
            }
            int rand = (int)(Math.random() * legalMoves.size());
            board = board.apply(legalMoves.get(rand));
            game.add(board);
        }
        return game;
    }
}
