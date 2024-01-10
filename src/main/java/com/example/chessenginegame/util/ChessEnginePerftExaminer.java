package com.example.chessenginegame.util;

import java.util.Collections;
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
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        PerftSettings perftRunner = new PerftSettings();
        while(true){
            System.out.println(DIRECTIONS_MESSAGE);
            try{
                int option = Integer.parseInt(reader.nextLine());
                if(option < 0 || option > 6){
                    throw new RuntimeException(String.format("'%s' is not a valid option", option));
                }

                if(option == SET_STARTING_POSITION){
                    setStartingPositionProcess(perftRunner);
                } else if(option == SET_DEPTH){
                    setDepthProcess((perftRunner));
                } else if(option == SHOW_STARTING_POSITION){
                    showStartingPosition(perftRunner);
                } else if(option == RUN_PERFT){
                    System.out.println("running perf testing......\n");
                } else if(option == STEP_INTO_MOVE){
                    System.out.println("stepping into move.....\n.");
                } else if(option == SAVE_SESSION){
                    System.out.println("save session......\n");
                }
                else {
                    break;
                }

            } catch(Exception e){
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
                if(input.length() > 4){
                    input = input.substring(0, 4);
                }
                if(input.equals("done")){
                    break;
                }
                perftRunner.applyMoveToBoardState(input);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Starting position has been saved.");
    }
    public static void setDepthProcess(PerftSettings perftRunner){
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your desired depth: ");
        while(true){
            String input = reader.nextLine();
            try{
                int depth = Integer.parseInt(input);
                if(depth < 0){
                    throw new RuntimeException("Depth must be a positive number");
                }
                perftRunner.setDepth(depth);
                break;
            } catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Please enter a positive number: ");
            }
        }
    }
    public static void showStartingPosition(PerftSettings perftRunner){
        Window window = new Window();
        window.setBoards(Collections.singletonList(perftRunner.getBoardState()));
        window.show();
    }
}
