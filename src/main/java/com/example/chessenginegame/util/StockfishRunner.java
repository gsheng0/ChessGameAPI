package com.example.chessenginegame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class StockfishRunner {
    public HashMap<String, Integer> getStockfishPerftNumbers(){
        HashMap<String, Integer> perftNumbers = new HashMap<>();
        try {
            Process stockfishProcess = new ProcessBuilder("stockfish").start();
            BufferedReader stockfishInput = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
            PrintWriter stockfishOutput = new PrintWriter(new OutputStreamWriter(stockfishProcess.getOutputStream()));

            // Wait for Stockfish to be ready
            stockfishOutput.println("uci");
            stockfishOutput.flush();

            // Read initial welcome message
            String response;
            while ((response = stockfishInput.readLine()) != null) {
                if (response.equals("uciok")) {
                    break;
                }
            }

            // Send UCI commands and read Stockfish responses
            String[] uciCommands = {"position startpos moves b2b3", "go perft 3"};
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

    }
}