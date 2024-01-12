package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.piece.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class ChessEnginePerftExaminer {
    private static final int SET_STARTING_POSITION = 1;
    private static final int SET_DEPTH = 2;
    private static final int SHOW_STARTING_POSITION = 3;
    private static final int RUN_PERFT = 4;
    private static final int STEP_INTO_MOVE = 5;
    private static final int SAVE_SESSION = 6;
    public static final String DIRECTIONS_MESSAGE =
                    "0. End\n" +
                    "1. Set starting position\n" +
                    "2. Set depth\n" +
                    "3. Show starting position\n" +
                    "4. Run perft\n" +
                    "5. Step into move\n" +
                    "6. Save session to file\n";

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        PerftSettings perftRunner = new PerftSettings();
        while (true) {
            System.out.println(DIRECTIONS_MESSAGE);
            try {
                int option = Integer.parseInt(reader.nextLine());
                if (option < 0 || option > 6) {
                    System.out.println(String.format("'%s' is not a valid option", option));
                    continue;
                }

                if (option == SET_STARTING_POSITION) {
                    setStartingPositionProcess(perftRunner);
                } else if (option == SET_DEPTH) {
                    setDepthProcess((perftRunner));
                } else if (option == SHOW_STARTING_POSITION) {
                    System.out.println("Show starting position: \n");
                    showStartingPosition(perftRunner);
                    System.out.println("Done showing starting position. \n");
                } else if (option == RUN_PERFT) {
                    System.out.println("running perf testing......\n");
                } else if (option == STEP_INTO_MOVE) {
                    System.out.println("stepping into move.....\n.");
                } else if (option == SAVE_SESSION) {
                    System.out.println("save session......\n");
                } else { // END
                    break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void setStartingPositionProcess(PerftSettings perftRunner){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter starting moves: " );
        while(true){
            try{
                String input = reader.nextLine();
                if(input == null || input.equals("done")){
                    break;
                }
                if(input.length() >= 4){
                    input = input.substring(0, 4);
                }
                else {
                    continue;
                }
                perftRunner.applyMoveToBoardState(input);
                showStartingPosition(perftRunner);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Starting position has been saved.");
    }

    public static void setStartingPositionProcess2(PerftSettings perftRunner) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter starting moves: ");
        while (true) {
            String input = null;
            Piece piece = null;
            Integer startTile = null, endTile = null;
            try {
                System.out.println("-- which piece? ");
                input = reader.nextLine();
                piece = Piece.of(input.substring(0, 1));
            } catch (Exception e) {
                System.out.println("Invalid piece: " + input == null ? "NULL" : input);
                break;
            }
            input = "NONE";
            try {
                System.out.println("-- starting tile? ");
                input = reader.nextLine();
                startTile = Integer.valueOf(input);
                if (startTile > Board.MAX_TILE || startTile < Board.MIN_TILE) {
                    throw new Exception("tile " + startTile + " out of range ");
                }
            } catch (Exception e) {
                System.out.println("Invalid starting tile: " + input == null ? "NULL" : input);
                break;
            }
            input = "NONE";
            try {
                System.out.println("-- ending tile? ");
                input = reader.nextLine();
                endTile = Integer.valueOf(input);
                if (endTile > Board.MAX_TILE || endTile < Board.MIN_TILE) {
                    throw new Exception("tile " + endTile + " out of range ");
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            perftRunner.applyMoveToBoardState(new Move(piece, startTile, endTile));
        }
        System.out.println("Starting position has been saved.\n\n");
    }

    public static void setDepthProcess(PerftSettings perftRunner) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your desired depth: ");
        while (true) {
            String input = reader.nextLine();
            try {
                int depth = Integer.parseInt(input);
                if (depth < 0) {
                    throw new RuntimeException("Depth must be a positive number");
                }
                perftRunner.setDepth(depth);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a positive number: ");
            }
        }
    }

    public static void showStartingPosition(PerftSettings perftRunner) {
//        Window window = new Window();
//        window.setBoards(Collections.singletonList(perftRunner.getBoardState()));
//        window.show();
        perftRunner.getBoardState().printBoardMatrix();
    }

    public static void runPerft(PerftSettings perfRunner) {

    }
}
