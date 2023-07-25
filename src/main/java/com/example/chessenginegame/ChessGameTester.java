package com.example.chessenginegame;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;
import com.example.chessenginegame.util.Constants;
import org.apache.tomcat.util.bcel.Const;

import java.util.List;

public class ChessGameTester {
    MoveGeneratorService moveGeneratorService = new MoveGeneratorServiceImpl();
    public int countMoves(Board board, int depth, int limit){
        if(depth == limit){
            return 1;
        }
        String color = Constants.WHITE;
        if(depth % 2 == 1){
            color = Constants.BLACK;
        }
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, color);
        int count = 0;
        for(Move move : moves){
            count += countMoves(board.apply(move), depth + 1, limit);
        }
        return count;
    }
    public static void main(String[] args){
        ChessGameTester tester = new ChessGameTester();
        Board board = Board.startingPosition();
        System.out.println(tester.countMoves(board, 0, 2));

    }
}
