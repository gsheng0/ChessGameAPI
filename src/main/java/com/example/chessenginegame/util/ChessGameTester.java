package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
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
        int movesPossible = count / moves.size();
        if(movesPossible != 1)
            System.out.println(movesPossible + " moves possible for " + color + " at depth " + depth);
        return count;
    }
    public List<Board> generateMoves(Board board, int depth, int limit){
        if(depth == limit){
            return Collections.singletonList(board);
        }
        List<Board> output = new ArrayList<>();
        String color = Constants.WHITE;
        if(depth % 2 == 1){
            color = Constants.BLACK;
        }
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, color);
        for(Move move : moves){
            output.addAll(generateMoves(board.apply(move), depth + 1, limit));
        }
        return output;
    }

    public List<Tuple<Board, List<Move>>> generateMovesWithHistory(Board board, int depth, int limit){
        if(depth == limit){
            return Collections.singletonList(Tuple.of(board, new ArrayList<>()));
        }
        List<Tuple<Board, List<Move>>> output = new ArrayList<>();
        String color = Constants.WHITE;
        if(depth % 2 == 1){
            color = Constants.BLACK;
        }
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, color);
        for(Move move : moves){
            List<Tuple<Board, List<Move>>> result = generateMovesWithHistory(board.apply(move), depth + 1, limit);
            for(Tuple<Board, List<Move>> tuple : result){
                tuple.getSecond().add(0, move);
            }
            output.addAll(result);
        }
        return output;
    }
    public static void main(String[] args){
        ChessGameTester tester = new ChessGameTester();
        Board board = Board.startingPosition();
        List<Tuple<Board, List<Move>>> twoMoves = tester.generateMovesWithHistory(board, 0, 2);
        for(Tuple<Board, List<Move>> tuple : twoMoves){
            Board boardState = tuple.getFirst();
            List<Move> history = tuple.getSecond();
            int moveCount = tester.countMoves(boardState, 0, 1);
            System.out.println(history.get(0).getSimpleName() + " " + history.get(1).getSimpleName() + ": " + moveCount);
        }

        System.out.println(twoMoves.size());

    }
}
