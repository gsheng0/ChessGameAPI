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
    // passed tests for caught paths with non-matched leaf node count
    @Test
    public void test_whenWhiteKingCheckedByBlackQueen() { validateUciMoves(Arrays.asList("c2c3", "d7d5", "d1a4")); }
    @Test
    public void test_noDiffWithStartPos_c2c3_b8c6_d1a4() {Assertions.assertTrue(validateUciMoves(Arrays.asList("c2c3", "b8c6", "d1a4")));}
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
    @Test
    public void test_getRandomUciMove_depth_4_total_2() {
        List<List<String>> paths = getRandomStartingPaths(Collections.emptyList(), 4, 2);
        for(List<String> path : paths) {
            System.out.println(path);
        }
        Assertions.assertEquals(2, paths.size());
    }
    @Test
    public void test_validate_d2d3_g7g5_e1d2_a7a5 () { validateUciMoves(Arrays.asList("d2d3", "g7g5", "e1d2", "a7a5")); }
    @Test
    public void test_validate_f2f3_e7e6_a2a4_d8h4 () { validateUciMoves(Arrays.asList("f2f3", "e7e6", "a2a4", "d8h4")); }
    @Test
    public void test_validate_d2d4_g8f6_g2g3_f6e4 () { validateUciMoves(Arrays.asList("d2d4", "g8f6", "g2g3", "f6e4")); }
    @Test
    public void test_validate_f2f4_a7a6_e1f2_d7d5() { validateUciMoves(Arrays.asList("f2f4", "a7a6", "e1f2", "d7d5")); }
    @Test
    public void test_validate_d2d4_g7g6_e1d2_c7c5() { validateUciMoves(Arrays.asList("d2d4", "g7g6", "e1d2", "c7c5")); }


    @Test
    public void test_validate_d2d4_c7c6_d1d3_f7f6_d3g6() { validateUciMoves(Arrays.asList("d2d4", "c7c6", "d1d3", "f7f6", "d3g6")); }

    private final List<String> startingUciMoves = Arrays.asList("d2d4", "c7c6", "d1d3", "f7f6", "d3g6");
    private final int depthForSummaryReport = 2, maxIterations = 1, depthToFindThePath = 4, maxPathToCheck = 1000;

    @Test  // print a summary of  number differences
    public void test_getSummary() {
        int depthForSummaryReport = 2;
        Map<String, Integer> myPerft = countLeafNodes(startingUciMoves, depthForSummaryReport);
        Map<String, Integer> stockfishPerft = StockfishRunner.getStockfishPerftNumbers(startingUciMoves, depthForSummaryReport);
        Map<String, Integer> differences = comparePerftResults(stockfishPerft, myPerft);
        printDiff(stockfishPerft, myPerft, differences);
    }
    @Test  // print a path that generates missing steps; paths are randomly selected to check
    public void test_findFirstPath() {
        int count = 0;
        while (count < maxIterations) {
            count ++;
            System.out.println(count + "). Generating " + maxPathToCheck + " paths of depth " + depthToFindThePath);
            List<List<String>> paths = getRandomStartingPaths(startingUciMoves, depthToFindThePath, maxPathToCheck);
            int pathCnt = 0;
            for (List<String> path : paths) {
                pathCnt ++;
                System.out.print(" " + pathCnt + ". validating " + path + "......");
                if (validateUciMoves(path)) {
                    System.out.println("valid.");
                } else {
                    System.out.println();
                    count = maxIterations; // stop after finding any
                    break;
                }
            }
        }
    }
    /**
     * Return a list of randomly selected path to check
     * @param depth number of moves from the starting position
     * @param total number of starting positions
     * @return list of [total] list of [depth] uciMoves
     */
    private List<List<String>> getRandomStartingPaths(List<String> startFromUciMoves, int depth, int total) {
        if (depth < 1) {
            return Collections.emptyList();
        }
        List<List<String>> startingPaths = new ArrayList<>();
        Board board = Board.startingPosition().applyUciMoves(startFromUciMoves);
        MoveTreeNode root = moveGenerator.generateLegalMovesTree(null, board, depth);
        for (int i=0; i<total; i++) {
            List<String> uciMoves = new ArrayList<>(startFromUciMoves);
            MoveTreeNode currNode = root;
            for (int j=0; j<depth; j++) {
                int selectedKidIndex = (int)(Math.random() * currNode.getKids().size());
                currNode = (MoveTreeNode) currNode.getKids().toArray()[selectedKidIndex];
                uciMoves.add(Objects.requireNonNull(currNode.move).getUCINotation());
            }
            startingPaths.add(uciMoves);
        }
        return startingPaths;
    }
    /**
     * Run perft for depth 1 with starting position of uciMoves; print out move counts and move diff;
     * @param uciMoves moves in UCI
     * @return return true if no diff.
     */
    private boolean validateUciMoves(List<String> uciMoves) {
        Board board = Board.startingPosition();
        for (String uciMove : uciMoves) {
            board = board.apply(uciMove);
        }
        MoveTreeNode root = moveGenerator.generateLegalMovesTree(null, board, 1);
        Map<String, Integer> myPerft = root.getLeafNodeCounts();
        Map<String, Integer> stockfishPerft = StockfishRunner.getStockfishPerftNumbers(uciMoves, 1);
        Map<String, Integer> differences = comparePerftResults(stockfishPerft, myPerft);
        if (differences.size() > 0) {
            System.out.print("\nStarting moves: " + uciMoves);
            System.out.println(" ---> " + (uciMoves.size()%2 == 0 ? "White" : "Black"));
            System.out.print("stockfish: " + stockfishPerft.size() + ", actual: " + myPerft.size());
            System.out.print(", delta: " + (myPerft.size() - stockfishPerft.size()));
            board.printBoardMatrix();
            printDiff(stockfishPerft, myPerft, differences);
        }
        return differences.size() == 0;
    }
    /**
     * Counting after tree built
     * @param startingUciMoves starting moves
     * @param depth number of moves
     * @return first move to count of leaf nodes map
     */
    private Map<String, Integer> countLeafNodes(List<String> startingUciMoves, int depth) {
        Board board = Board.startingPosition().applyUciMoves(startingUciMoves);
        List<Move> moves = moveGenerator.generateLegalMoves(board);
        Map<String, Integer> perft = new HashMap<>();
        for(Move move : moves) {
            int count = countMoves(board.apply(move), depth - 1);
            perft.put(move.getUCINotation(), count);
        }
        return perft;
    }
    /**
     * Counting without generating the tree, so as to go deeper
     * @param board starting board
     * @param depth depth to go
     * @return map of difference
     */
    private int countMoves(Board board, int depth){
        if(depth == 0){
            return 1;
        }
        List<Move> moves = moveGenerator.generateLegalMoves(board);
        int count = 0;
        for(Move move : moves){
            count += countMoves(board.apply(move), depth - 1);
        }
        return count;
    }
    /**
     * generate the difference map
     * @param stockfishResults results from stock fish
     * @param myResults results from my implementation
     * @return return map of (move, myCount - stockfishCount)
     */
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
    /**
     *
     * @param stockfishResults results from stock fish
     * @param myResults results from my implementation
     * @param differences diff map
     * print list of "move: xxx, expected: stockfishCount, actual: myCount";
     */
    private void printDiff (Map<String, Integer> stockfishResults, Map<String, Integer> myResults, Map<String, Integer> differences) {
        for (String uciMove : differences.keySet()) {
            System.out.println(uciMove + ": " + differences.get(uciMove) +
                    ", expected: " + stockfishResults.get(uciMove) +
                    ", actual: " + myResults.get(uciMove));
        }
    }
}
