package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;
import com.example.chessenginegame.service.MoveGeneratorService;
import com.example.chessenginegame.service.MoveGeneratorServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StockfishRunner {
    private static final String SET_POSITION_PREFIX = "position startpos moves ";
    private static final String START_PERFT_PREFIX = "go perft ";
    private static final String STOCKFISH_READY = "uciok";
    private static final String STOCKFISH_START_COMMAND = "stockfish";
    private static final String STOCKFISH_UCI_COMMAND = "uci";
    /**
     * @param moves list of moves to apply as the starting point
     * @param depth the depth to go to
     * @return a hashmap mapping each first move to the number of possible positions to reach from that move
     * A depth of 1 returns a hashmap mapping every starting move to the number 1, because at a depth of one, the perft stops
     * at the starting move, eg: There is only one position possible from each starting move with one move allowed
     */
    public static HashMap<Move, Integer> getStockfishPerftNumbers(List<Move> moves, int depth){
        HashMap<Move, Integer> perftNumbers = new HashMap<>();
        try {
            Process stockfishProcess = new ProcessBuilder(STOCKFISH_START_COMMAND).start();
            BufferedReader stockfishInput = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
            PrintWriter stockfishOutput = new PrintWriter(new OutputStreamWriter(stockfishProcess.getOutputStream()));
            String response;

            // Send UCI command to communicate with Stockfish
            stockfishOutput.println(STOCKFISH_UCI_COMMAND);
            stockfishOutput.flush();

            // Wait for the engine to signal it's ready
            while ((response = stockfishInput.readLine()) != null) {
                if (response.equals(STOCKFISH_READY)) {
                    break;
                }
            }

            // Build the position by appending the moves in UCI notation
            StringBuilder setPositionString = new StringBuilder();
            setPositionString.append(SET_POSITION_PREFIX);
            moves.stream().map(Move::getUCINotation).forEach(moveString -> {
                setPositionString.append(moveString);
                setPositionString.append(" ");
            });
            String[] uciCommands = {setPositionString.toString(), START_PERFT_PREFIX + depth};

            // Starts perft
            for (String cmd : uciCommands) {
                stockfishOutput.println(cmd);
                stockfishOutput.flush();
            }

            Board board = Board.startingPosition().apply(moves);
            // Read Stockfish output to collect perft numbers for each move
            while ((response = stockfishInput.readLine()) != null) {
                if(response.equals("")){
                    break;
                }
                String[] split = response.split(":");
                perftNumbers.put(Move.parseUCIMove(board, split[0]), Integer.parseInt(split[1].substring(1)));
            }
            stockfishProcess.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return perftNumbers;
    }

    public static void main(String[] args) {
        Move firstMove = Move.parseUCIMove(Board.startingPosition(), "b2b3");
        HashMap<Move, Integer> perftNumbers = getStockfishPerftNumbers(Collections.singletonList(firstMove), 2);
        for(Move move : perftNumbers.keySet()){
            System.out.println(move.getUCINotation() + ": " + perftNumbers.get(move));
        }
    }
}