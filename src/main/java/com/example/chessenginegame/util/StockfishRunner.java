package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class StockfishRunner {
    private static final String SET_POSITION_PREFIX = "position startpos moves ";
    private static final String STOCKFISH_READY = "uciok";
    private static final String STOCKFISH_START_COMMAND = "stockfish";
    private static final String STOCKFISH_UCI_COMMAND = "uci";
    /**
     * @param moves list of moves to apply as the starting point
     * @param depth the depth to go to
     * @return
     */
    public static HashMap<String, Integer> getStockfishPerftNumbers(List<Move> moves, int depth){
        HashMap<String, Integer> perftNumbers = new HashMap<>();
        try {
            Process stockfishProcess = new ProcessBuilder(STOCKFISH_START_COMMAND).start();
            BufferedReader stockfishInput = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
            PrintWriter stockfishOutput = new PrintWriter(new OutputStreamWriter(stockfishProcess.getOutputStream()));
            String response;

            stockfishOutput.println(STOCKFISH_UCI_COMMAND);
            stockfishOutput.flush();
            while ((response = stockfishInput.readLine()) != null) {
                if (response.equals(STOCKFISH_READY)) {
                    break;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(SET_POSITION_PREFIX);
            moves.stream().map(Move::getUCINotation).forEach(moveString -> {
                stringBuilder.append(moveString);
                stringBuilder.append(" ");
            });
            String[] uciCommands = {stringBuilder.toString(), "go perft 3"};
            for (String cmd : uciCommands) {
                stockfishOutput.println(cmd);
                stockfishOutput.flush();
            }

            // Read and process Stockfish responses
            while ((response = stockfishInput.readLine()) != null) {
                System.out.println(response);
                if(response.startsWith("Nodes")){
                    break;
                }
            }

            stockfishProcess.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return perftNumbers;
    }
    public static void main(String[] args) {
        getStockfishPerftNumbers();
    }
}