package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.MoveTreeNode;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.util.StockfishRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class StockFishPerfTest extends MoveGeneratorServiceImplTest {

    @Test
    public void test_noStartMovePerftDepth_4() {
        List<String> startingUciMoves = Collections.EMPTY_LIST;
        int depth = 4;
        Board board = Board.startingPosition();
        MoveTreeNode root = moveGenerator.generateLegalMovesTree(null, board, Constants.WHITE, depth);
        Map<String, Integer> myPerft = root.getLeafNodeCounts();
        Map<String, Integer> stockfishPerft = StockfishRunner.getStockfishPerftNumbers(startingUciMoves, depth);
        Map<String, Integer> differences = comparePerftResults(stockfishPerft, myPerft);
        printDiff(myPerft, stockfishPerft, differences);
        int maxCount = 10; // max # of examples
        System.out.println("\nExample (max " + maxCount + " per starting move):\n");
        for (String uciMove : differences.keySet()) {
            System.out.println("mismatch count of starting uciMove: " + uciMove);
            int cnt = 0;
            for (MoveTreeNode diffMoveNode : root.findMoveNodes(uciMove, 1)) { // only 1
                List<List<String>> uciMovesList = root.getUciMovePathToLeafNode(diffMoveNode);
                System.out.println(uciMove + ": " + uciMovesList.size());
            }
        }
    }

    @Test
    public void test_whenWhiteKingCheckedByBlackQueen() { validateUciMoves(Arrays.asList("c2c3", "d7d5", "d1a4")); }

    @Test
    public void test_noDiffWithStartPos_c2c3_b8c6_d1a4() {
        Assertions.assertTrue(validateUciMoves(Arrays.asList("c2c3", "b8c6", "d1a4")));
    }

    @Test
    public void test_noPinsWithStartPos_c2c3_b8c6_d1a4() {
        Board board = Board.startingPosition();
        String oppositeColor = Constants.WHITE;
        for (String uciMove : Arrays.asList("c2c3", "b8c6", "d1a4")) {
            board = board.apply(uciMove);
            oppositeColor = Piece.getOppositeColor(oppositeColor);
        }
        Assertions.assertEquals(Collections.EMPTY_LIST, moveGenerator.getPins(board, Constants.BLACK));
    }

    private boolean validateUciMoves(List<String> uciMoves) {
        Board board = Board.startingPosition();
        String oppositeColor = Constants.WHITE;
        for (String uciMove : uciMoves) {
            board = board.apply(uciMove);
            oppositeColor = Piece.getOppositeColor(oppositeColor);
        }
        MoveTreeNode root = moveGenerator.generateLegalMovesTree(null, board, oppositeColor, 1);
        Map<String, Integer> myPerft = root.getLeafNodeCounts();
        Map<String, Integer> stockfishPerft = StockfishRunner.getStockfishPerftNumbers(uciMoves, 1);
        Map<String, Integer> differences = comparePerftResults(stockfishPerft, myPerft);
        if (differences.size() > 0) {
            System.out.println("Starting moves: " + uciMoves);
            System.out.print("stockfish: " + stockfishPerft.size() + ", actual: " + myPerft.size());
            System.out.print(", delta: " + (myPerft.size() - stockfishPerft.size()));
            board.printBoardMatrix();
            printDiff(stockfishPerft, myPerft, differences);
        }
        return differences.size() == 0;
    }

    private Map<String, Integer> comparePerftResults(Map<String, Integer> stockfishResults, Map<String, Integer> myResults){
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

// obsolete testing
//    @Test
//    public void test_setStartPosRunBothUsingOldCountMethodPrintDiff() {
//        int depth = 1;
//        List<String> uciMoves = Arrays.asList("c2c3", "d7d5", "d1a4");
//        Map<String, Integer> perftResults = doPerftFromPosition(uciMoves, depth);
//        Map<String, Integer> stockfishPerftResults = StockfishRunner.getStockfishPerftNumbers(uciMoves, depth);
//        Map<String, Integer> differences = comparePerftResults(stockfishPerftResults, perftResults);
//        printDiff(perftResults, stockfishPerftResults, differences);
//    }
//
//    /**
//     * @param uciMoves starting moves in UCI notation
//     * @param depth The depth to go to
//     * @return a hashmap mapping each first move to the number of possible positions to reach from that move
//     * A depth of 1 returns a hashmap mapping every starting move to the number 1, because at a depth of one, the perft stops
//     * at the starting move, eg: There is only one position possible from each starting move with one move allowed
//     */
//    protected HashMap<String, Integer> doPerftFromPosition(List<String> uciMoves, int depth) {
//        HashMap<String, Integer> perftResults = new HashMap<>();
//        Board board = Board.startingPosition();
//        String startingColor = Constants.WHITE;
//        for (String uciMove : uciMoves) {
//            board = board.apply(Move.parseUCIMove(board, uciMove));
//            startingColor = Piece.getOppositeColor(startingColor);
//        }
//        List<Move> moves = moveGenerator.generateLegalMoves(board, startingColor);
//        String oppositeColor = Piece.getOppositeColor(startingColor);
//        for(Move move : moves){
//            Board currentBoard = board.apply(move);
//            int moveCount = countMoves(currentBoard, depth - 1, oppositeColor);
//            perftResults.put(move.getUCINotation(), moveCount);
//        }
//        return perftResults;
//    }
//
//    public int countMoves(Board board, int depth, String startingColor) {
//        if(depth == 0){
//            return 1;
//        }
//        String oppositeSide = Piece.getOppositeColor(startingColor);
//        List<Move> moves = moveGenerator.generateLegalMoves(board, startingColor);
//        int count = 0;
//        for(Move move : moves){
//            count += countMoves(board.apply(move), depth - 1, oppositeSide);
//        }
//        return count;
//    }
//
//    public int countMoves(Board board, int depth, int limit){
//        if(depth == limit){
//            return 1;
//        }
//        String color = Constants.WHITE;
//        if(depth % 2 == 1){
//            color = Constants.BLACK;
//        }
//        List<Move> moves = moveGenerator.generateLegalMoves(board, color);
//        int count = 0;
//        for(Move move : moves){
//            count += countMoves(board.apply(move), depth + 1, limit);
//        }
//        return count;
//    }

}
