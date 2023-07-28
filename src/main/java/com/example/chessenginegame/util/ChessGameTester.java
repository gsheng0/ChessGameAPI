package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChessGameTester {
    static MoveGeneratorService moveGeneratorService = new MoveGeneratorServiceImpl();
    public int countMoves(Board board, int depth, String startingColor){
        if(depth == 0){
            return 1;
        }
        String oppositeSide = PieceUtil.getOppositeColor(startingColor);
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, startingColor);
        int count = 0;
        for(Move move : moves){
            count += countMoves(board.apply(move), depth - 1, oppositeSide);
        }
        return count;
    }
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
    public HashMap<Move, Integer> doPerftFromPosition(Board board, int depth, String side){
        HashMap<Move, Integer> perftResults = new HashMap<>();
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, side);
    }
    public static void main(String[] args){
        ChessGameTester tester = new ChessGameTester();
        Board board = Board.startingPosition();
        board = board.apply(Move.parseUCIMove(board, "b2b3"));
        List<Tuple<Board, List<Move>>> oneMove = tester.generateMovesWithHistory(board, 1, 2);
        int totalCount = 0;
        for(Tuple<Board, List<Move>> tuple : oneMove){
            Board boardState = tuple.getFirst();
            List<Move> history = tuple.getSecond();
            int moveCount = tester.countMoves(boardState, 2, 4);
            totalCount += moveCount;
            String boardVariation = history.stream().map(Move::getUCINotation).reduce((s, s2) -> s + " " + s2).get();
            System.out.println(boardVariation + ": " + moveCount);
        }
        System.out.println(totalCount);
    }
}
