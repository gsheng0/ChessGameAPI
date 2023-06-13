package com.example.chessenginegame.components;

import java.util.ArrayList;

import static com.example.chessenginegame.components.Piece.*;

public class Position {

    private int[] board;
    private int turn = WHITE;
    private boolean whiteKingSideCastle;
    private boolean whiteQueenSideCastle;
    private boolean blackKingSideCastle;
    private boolean blackQueenSideCastle;

    private Position(int[] board, int turn, boolean whiteKingSideCastle, boolean whiteQueenSideCastle, boolean blackKingSideCastle, boolean blackQueenSideCastle){
        this.board = board;
        this.turn = turn;
        this.whiteKingSideCastle = whiteKingSideCastle;
        this.whiteQueenSideCastle = whiteQueenSideCastle;
        this.blackKingSideCastle = blackKingSideCastle;
        this.blackQueenSideCastle = blackQueenSideCastle;
    }
    public boolean canWhiteKingSideCastle() { return whiteKingSideCastle; }
    public boolean canWhiteQueenSideCastle() { return whiteQueenSideCastle; }
    public boolean canBlackKingSideCastle() { return blackKingSideCastle; }
    public boolean canBlackQueenSideCastle() { return blackQueenSideCastle; }
    public void setWhiteKingSideCastle(boolean whiteKingSideCastle){
        this.whiteKingSideCastle = whiteKingSideCastle;
    }
    public void setWhiteQueenSideCastle(boolean whiteQueenSideCastle){
        this.whiteQueenSideCastle = whiteQueenSideCastle;
    }
    public void setBlackKingSideCastle(boolean blackKingSideCastle){
        this.blackKingSideCastle = blackKingSideCastle;
    }
    public void setBlackQueenSideCastle(boolean blackQueenSideCastle){
        this.blackQueenSideCastle = blackQueenSideCastle;
    }

    /**
     *
     * @param index the piece to be chosen
     * @returns a list of moves for the piece at the specified index
     * @throws IllegalArgumentException when the specified tile is empty
     */
    public ArrayList<Integer> getMoves(int index){
        ArrayList<Integer> output = new ArrayList<>();
        return output;
    }

    /**
     *
     * @param coordinates the string representation of a chess tile ie: "g3" or "F7"
     *                    Only the first two characters are checked
     * @return the corresponding index in the 1d representation of the board. Passing in "A8" returns 0
     * @throws IllegalArgumentException if an invalid coordinate is entered.
     */
    public static int getIndexFromNamedTile(String coordinates){
        coordinates = coordinates.toLowerCase();
        int letter = coordinates.charAt(0) - 'a';
        int number = Integer.parseInt(coordinates.substring(1));
        if(number < 1 || number > 8 || letter < 0 || letter > 7){
            throw new IllegalArgumentException("Invalid input: " + coordinates);
        }
        return letter + 8 * (8 - number);
    }

    /**
     *
     * @param FEN Fen string representing the board state
     *        FEN strings are in the format of the following, each separated by a single space
     *            1. Board state, with characters representing pieces, and numbers that many empty squares,
     *               starting from the top left corner of the board, from white's perspective
     *            2. The side whose turn it is
     *            3. The availability of castling for each side, K representing king side castling, and q for queen side castling
     *            4. Available en passant squares
     *            5. Half move counter
     *            6. Full move counter
     * @return A position object containing that corresponding board state
     * @throws IllegalArgumentException if invalid character is encountered
     */
    public static Position createFromFEN(String FEN){
        String[] split = FEN.split(" ");
        String boardString = split[0];
        String turnString = split[1];
        String castleString = split[2];

        int[] board = createBoardFromString(boardString);
        int turn = getTurnFromString(turnString);
        boolean whiteKingSideCastle = castleString.contains("K");
        boolean whiteQueenSideCastle = castleString.contains("Q");
        boolean blackKingSideCastle = castleString.contains("k");
        boolean blackQueenSideCastle = castleString.contains("q");


        Position position = new Position(board, turn, whiteKingSideCastle, whiteQueenSideCastle, blackKingSideCastle, blackQueenSideCastle);
        return position;
    }

    /**
     *
     * @param boardString the board portion of a FEN string
     * @return the integer representation of the position specified by the input
     */
    private static int[] createBoardFromString(String boardString){
        int[] board = new int[64];
        int index = 0;
        for(int i = 0; i < boardString.length(); i++){
            char current = boardString.charAt(i);
            if(current == '/'){
                continue;
            }
            if(current > '0' && current < '9'){
                int num = current - '0';
                index += num;
            }
            else{
                board[index] = convertCharPiece(current);
                index++;
            }
        }
        return board;
    }

    /**
     *
     * @param turnString the portion of the FEN string that indicates which side's turn it is
     * @return the integer representation of the side whose turn it is
     * @throws IllegalArgumentException if the turnString's first character is not either a 'w' or a 'b'
     */
    private static int getTurnFromString(String turnString){
        int turn = 0;
        turnString = turnString.toLowerCase();
        if(turnString.charAt(0) == 'w'){
            turn = WHITE;
        }
        else if(turnString.charAt(0) == 'b'){
            turn = BLACK;
        }
        else{
            throw new IllegalArgumentException("Invalid turn string: " + turnString);
        }
        return turn;
    }

    /**
     *
     * Prints the board state to the console, along with all the other information contained within a fen string
     */
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 64; i++){
            if(i != 0 && i % 8 == 0){
                builder.append("\n");
            }
            if(board[i] == 0){
                builder.append("_ ");
            }
            else{
                builder.append(convertIntegerPiece(board[i]));
                builder.append(" ");
            }
        }
        builder.append("\n");
        return builder.toString();
    }
    public static void main(String[] args){
        Position pos = createFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        for(int i = 0; i < 64; i++){
            if(i % 8 == 0){
                System.out.println();
            }
            if(pos.board[i] == 0){
                System.out.print("_ ");
            }
            else{
                System.out.print(convertIntegerPiece(pos.board[i]) + " ");
            }
        }
    }
}
