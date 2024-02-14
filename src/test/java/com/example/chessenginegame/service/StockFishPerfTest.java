package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
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
    @Test
    public void test_getRandomUciMove_depth_4_total_2() {
        List<List<String>> paths = getRandomStartingPaths(4, 2);
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
    @Test  // use this test to find bug
    public void test_validateRandomUciMovesUntilFailOrMaxIterations() {
        int maxIterations = 10, depth = 4, total = 100;
        int count = 0;
        while (count < maxIterations) {
            count ++;
            System.out.println(count + "). Generating " + total + " paths of depth " + depth);
            List<List<String>> paths = getRandomStartingPaths(depth, total);
            int pathCnt = 0;
            for (List<String> path : paths) {
                pathCnt ++;
                System.out.print(" " + pathCnt + ". validating " + path + "......");
                if (validateUciMoves(path)) {
                    System.out.println("valid.");
                } else {
                    System.out.println();
                    count = maxIterations;
                    break;
                }
            }
        }
    }




    /**
     *
     * @param depth number of moves forming the starting position
     * @param total number of starting positions
     * @return list of [total] list of [depth] uciMoves
     */
    private List<List<String>> getRandomStartingPaths(int depth, int total) {
        if (depth < 1) {
            return Collections.emptyList();
        }
        List<List<String>> startingPaths = new ArrayList<>();
        Board board = Board.startingPosition();
        MoveTreeNode root = moveGenerator.generateLegalMovesTree(null, board, Constants.WHITE, depth);
        for (int i=0; i<total; i++) {
            List<String> uciMoves = new ArrayList<>();
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
            System.out.println("\nStarting moves: " + uciMoves);
            System.out.print("stockfish: " + stockfishPerft.size() + ", actual: " + myPerft.size());
            System.out.print(", delta: " + (myPerft.size() - stockfishPerft.size()));
            board.printBoardMatrix();
            printDiff(stockfishPerft, myPerft, differences);
        }
        return differences.size() == 0;
    }
    /**
     *
     * @param stockfishResults
     * @param myResults
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
     * @param stockfishResults
     * @param myResults
     * @param differences
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
