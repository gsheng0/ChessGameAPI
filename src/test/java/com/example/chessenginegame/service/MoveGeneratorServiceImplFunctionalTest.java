package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.chessenginegame.util.StockfishRunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StockFishCompareTest extends MoveGeneratorServiceImplTest {

    @Test
    public void test_ok() {
        assertEquals(1, 1);
    }

    @Test
    public void test_1() {
        int depth = 3;
        Board board = Board.startingPosition();
        List<Move> moves = Move.listOf(board); //, "e2e4", "g7g6");
        board = board.apply(moves);
        //TODO: check king move logic
        HashMap<Move, Integer> perftResults = doPerftFromPosition(board, depth, Constants.WHITE);
        HashMap<Move, Integer> stockfishPerftResults = StockfishRunner.getStockfishPerftNumbers(moves, depth);
        HashMap<Move, Integer> differences = comparePerftResults(stockfishPerftResults, perftResults);
        for (Move move : differences.keySet()) {
            System.out.println(move.getUCINotation() + ": " + differences.get(move) +
                    " expected: " + stockfishPerftResults.get(move) +
                    " actual: "  + perftResults.get(move));
        }
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
     protected HashMap<Move, Integer> doPerftFromPosition(Board board, int depth, String startingColor){
        HashMap<Move, Integer> perftResults = new HashMap<>();
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, startingColor);

        String oppositeColor = Piece.getOppositeColor(startingColor);
        for(Move move : moves){
            Board currentBoard = board.apply(move);
            int moveCount = countMoves(currentBoard, depth - 1, oppositeColor);
            perftResults.put(move, moveCount);
        }
        return perftResults;
    }

    public int countMoves(Board board, int depth, String startingColor) {
        if(depth == 0){
            return 1;
        }
        String oppositeSide = Piece.getOppositeColor(startingColor);
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

    public void printPerftResults(HashMap<Move, Integer> perftResults){
        for(Move move : perftResults.keySet()){
            System.out.println(move.getUCINotation() + ": " + perftResults.get(move));
        }
    }
}
