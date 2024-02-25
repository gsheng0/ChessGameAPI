package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.util.*;
//
public class ChessGameTester {
    //TODO: Make all methods static, refactor methods that have both a depth and limit parameter
    //TODO: Make command line type program loop to navigate perft results and possibly rerun perfts
    //TODO: Make perft detect which side is first to act
    static MoveGeneratorService moveGeneratorService = new MoveGeneratorServiceImpl();
    public static int countMoves(Board board, int depth, String startingColor){
        if(depth == 0){
            return 1;
        }
        String oppositeSide = Piece.getOppositeColor(startingColor);
        List<Move> moves = moveGeneratorService.generateLegalMoves(board);
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
        List<Move> moves = moveGeneratorService.generateLegalMoves(board);
        int count = 0;
        for(Move move : moves){
            count += countMoves(board.apply(move), depth + 1, limit);
        }
        return count;
    }
    public static List<Board> generateMoves(Board board, int depth, int limit){
        if(depth == limit){
            return Collections.singletonList(board);
        }
        List<Board> output = new ArrayList<>();
        String color = Constants.WHITE;
        if(depth % 2 == 1){
            color = Constants.BLACK;
        }
        List<Move> moves = moveGeneratorService.generateLegalMoves(board);
        for(Move move : moves){
            output.addAll(generateMoves(board.apply(move), depth + 1, limit));
        }
        return output;
    }

    public List<Pair<Board, List<Move>>> generateMovesWithHistory(Board board, int depth, int limit){
        if(depth == limit){
            return Collections.singletonList(Pair.of(board, new ArrayList<>()));
        }
        List<Pair<Board, List<Move>>> output = new ArrayList<>();
        String color = Constants.WHITE;
        if(depth % 2 == 1){
            color = Constants.BLACK;
        }
        List<Move> moves = moveGeneratorService.generateLegalMoves(board);
        for(Move move : moves){
            List<Pair<Board, List<Move>>> result = generateMovesWithHistory(board.apply(move), depth + 1, limit);
            for(Pair<Board, List<Move>> pair : result){
                pair.right().add(0, move);
            }
            output.addAll(result);
        }
        return output;
    }

    /**
     *
     * @param board The starting position of the perft
     * @param depth The depth to go to
     * @param startingColor The side that goes first
     * @return a hashmap mapping each first move to the number of possible positions to reach from that move
     * A depth of 1 returns a hashmap mapping every starting move to the number 1, because at a depth of one, the perft stops
     * at the starting move, eg: There is only one position possible from each starting move with one move allowed
     */
    public static HashMap<Move, Integer> doPerftFromPosition(Board board, int depth, String startingColor){
        HashMap<Move, Integer> perftResults = new HashMap<>();
        List<Move> moves = moveGeneratorService.generateLegalMoves(board);

        String oppositeColor = Piece.getOppositeColor(startingColor);
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
    public static HashMap<Move, Integer> comparePerftResults(HashMap<Move, Integer> stockfishResults, HashMap<Move, Integer> myResults){
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
    //e8e7 -> missing move after "b2b3", "e7e6", "a2a3"
    public static void printPerftResults(HashMap<Move, Integer> perftResults){
        for(Move move : perftResults.keySet()){
            System.out.println(move.getUCINotation() + ": " + perftResults.get(move));
        }
    }
    public static void main(String[] args){
//        int depth = 1;
//        Board board = Board.startingPosition();
//        List<Move> moves = Move.listOf(board, "e2e4", "g7g6");
//        board = board.apply(moves);
//    //TODO: check king move logic
//        HashMap<Move, Integer> perftResults = doPerftFromPosition(board, depth, Constants.WHITE);
//        HashMap<Move, Integer> stockfishPerftResults = StockfishRunner.getStockfishPerftNumbers(moves, depth);
//        HashMap<Move, Integer> differences = comparePerftResults(stockfishPerftResults, perftResults);
//        for(Move move : differences.keySet()){
//            System.out.println(move.getUCINotation() + ": " + differences.get(move) + " expected: " + stockfishPerftResults.get(move) + " actual: " + perftResults.get(move));
//        }

//        Scanner reader = new Scanner(System.in);
//        while(true){
//
//
//        }
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
