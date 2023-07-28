package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.util.*;

public class ChessGameTester {
    //TODO: Make all methods static, refactor methods that have both a depth and limit parameter
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
    public HashMap<Move, Integer> doPerftFromPosition(Board board, int depth, String startingColor){
        HashMap<Move, Integer> perftResults = new HashMap<>();
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, startingColor);
        String oppositeColor = PieceUtil.getOppositeColor(startingColor);
        for(Move move : moves){
            Board currentBoard = board.apply(move);
            int moveCount = countMoves(currentBoard, depth - 1, oppositeColor);
            perftResults.put(move, moveCount);
        }
        return perftResults;
    }

    /**
     *
     * @param stockfishResults stockfish's perft results
     * @param myResults my perft results
     * @return a hash map mapping moves and the difference in move counts between stockfish's and my results. If a move is mapped to plus one,
     * that means that my perft found an extra move, compared to stockfish's perft
     */
    public HashMap<Move, Integer> comparePerftResults(HashMap<Move, Integer> stockfishResults, HashMap<Move, Integer> myResults){
        HashMap<Move, Integer> differences = new HashMap<>();
        Set<Move> moveSet = new HashSet<>(stockfishResults.keySet());
        moveSet.addAll(myResults.keySet());

        for(Move move : moveSet){
            if(!myResults.containsKey(move)){
                differences.put(move, -1 * stockfishResults.get(move));
            }
            else if(!stockfishResults.containsKey(move)){
                differences.put(move, myResults.get(move));
            }
            else{
                int difference = myResults.get(move) - stockfishResults.get(move);
                if(difference == 0){
                    continue;
                }
                differences.put(move, myResults.get(move) - stockfishResults.get(move));
            }
        }
        return differences;
    }
    public static void main(String[] args){
        ChessGameTester tester = new ChessGameTester();

        Board board = Board.startingPosition();
        List<Move> moves = Move.listOf(board, "b2b3", "e7e6", "a2a3");
        board = board.apply(moves);
        HashMap<Move, Integer> perftResults = tester.doPerftFromPosition(board, 1, Constants.BLACK);
        HashMap<Move, Integer> stockfishPerftResults = StockfishRunner.getStockfishPerftNumbers(moves, 1);
        HashMap<Move, Integer> differences = tester.comparePerftResults(stockfishPerftResults, perftResults);
        for(Move move : differences.keySet()){
            System.out.println(move.getUCINotation() + ": " + differences.get(move) + " expected: " + stockfishPerftResults.get(move));
        }
//        List<Tuple<Board, List<Move>>> oneMove = tester.generateMovesWithHistory(board, 1, 2);
//        int totalCount = 0;
//        for(Tuple<Board, List<Move>> tuple : oneMove){
//            Board boardState = tuple.getFirst();
//            List<Move> history = tuple.getSecond();
//            int moveCount = tester.countMoves(boardState, 2, 4);
//            totalCount += moveCount;
//            String boardVariation = history.stream().map(Move::getUCINotation).reduce((s, s2) -> s + " " + s2).get();
//            System.out.println(boardVariation + ": " + moveCount);
//        }
//        System.out.println(totalCount);
    }
}
