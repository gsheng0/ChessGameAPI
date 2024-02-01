package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.util.StockfishRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class StockFishPerfTest extends MoveGeneratorServiceImplTest {
    @Test
    public void test_ok() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    public void test_1() {
        int depth = 1;
        List<String> uciMoves = Arrays.asList("e2e4", "g7g6");
        HashMap<String, Integer> perftResults = doPerftFromPosition(uciMoves, depth);
        HashMap<String, Integer> stockfishPerftResults = StockfishRunner.getStockfishPerftNumbers(uciMoves, depth);
        HashMap<String, Integer> differences = comparePerftResults(stockfishPerftResults, perftResults);
        printDiff(perftResults, stockfishPerftResults, differences);
    }

    /**
     * @param uciMoves starting moves in UCI notation
     * @param depth The depth to go to
     * @return a hashmap mapping each first move to the number of possible positions to reach from that move
     * A depth of 1 returns a hashmap mapping every starting move to the number 1, because at a depth of one, the perft stops
     * at the starting move, eg: There is only one position possible from each starting move with one move allowed
     */
    protected HashMap<String, Integer> doPerftFromPosition(List<String> uciMoves, int depth) {
        HashMap<String, Integer> perftResults = new HashMap<>();
        Board board = Board.startingPosition();
        String startingColor = Constants.WHITE;
        for (String uciMove : uciMoves) {
            board = board.apply(Move.parseUCIMove(board, uciMove));
            startingColor = Piece.getOppositeColor(startingColor);
        }
        List<Move> moves = moveGeneratorService.generateLegalMoves(board, startingColor);
        String oppositeColor = Piece.getOppositeColor(startingColor);
        for(Move move : moves){
            Board currentBoard = board.apply(move);
            int moveCount = countMoves(currentBoard, depth - 1, oppositeColor);
            perftResults.put(move.getUCINotation(), moveCount);
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

    public HashMap<String, Integer> comparePerftResults(HashMap<String, Integer> stockfishResults, HashMap<String, Integer> myResults){
        HashMap<String, Integer> differences = new HashMap<>();
        Set<String> moveSet = new HashSet<>(stockfishResults.keySet());
        moveSet.addAll(myResults.keySet());

        for(String move : moveSet){
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

    public void printPerftResults(HashMap<String, Integer> perftResults){
        for(String uciMove : perftResults.keySet()){
            System.out.println(uciMove + ": " + perftResults.get(uciMove));
        }
    }

}
