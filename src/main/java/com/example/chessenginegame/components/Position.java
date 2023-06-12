package com.example.chessenginegame.components;

public class Position {
    public static final int EMPTY = 0;
    public static final int PAWN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int QUEEN = 5;
    public static final int KING = 6;
    public static final int WHITE = 16;
    public static final int BLACK = 8;
    private int[] board;
    public Position(){
        board = new int[64];
    }
    public Position(int[] board){
        this.board = board;
    }
    public void getMoves(int index){

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
        Position position = new Position(board);
        return position;
    }
    /**
     @param piece Integer representation of a piece
     @return A single character representing that piece. Uppercase for White, Lowercase for Black
            K for king, Q for queen, R for rook, B for bishop, N for Knight, and P for pawn
     @throws IllegalArgumentException if the piece parameter is not a valid piece
     */
    public static char convertIntegerPiece(int piece) {
        if(piece == (BLACK | PAWN)){
            return 'p';
        } else if(piece == (BLACK | KNIGHT)){
            return 'n';
        } else if(piece == (BLACK | BISHOP)){
            return 'b';
        } else if(piece == (BLACK | ROOK)){
            return 'r';
        } else if(piece == (BLACK | QUEEN)){
            return 'q';
        } else if(piece == (BLACK | KING)){
            return 'k';
        } else if(piece == (WHITE | PAWN)){
            return 'P';
        } else if(piece == (WHITE | KNIGHT)){
            return 'N';
        } else if(piece == (WHITE | BISHOP)){
            return 'B';
        } else if(piece == (WHITE | ROOK)){
            return 'R';
        } else if(piece == (WHITE | QUEEN)){
            return 'Q';
        } else if(piece == (WHITE | KING)){
            return 'K';
        }
        throw new IllegalArgumentException("Invalid piece representation: " + piece);
    }

    /**
     *
     * @param piece character representation of a piece
     * @return The integer representation of the piece
     * @throws IllegalArgumentException if the piece parameter is not a valid piece
     */
    public static int convertCharPiece(char piece){
        if(piece == 'p'){
            return BLACK | PAWN;
        } else if(piece == 'n'){
            return BLACK | KNIGHT;
        } else if(piece == 'b'){
            return BLACK | BISHOP;
        } else if(piece == 'r'){
            return BLACK | ROOK;
        } else if(piece == 'q'){
            return BLACK | QUEEN;
        } else if(piece == 'k'){
            return BLACK | KING;
        } else if(piece == 'P'){
            return WHITE | PAWN;
        } else if(piece == 'N'){
            return WHITE | KNIGHT;
        } else if(piece == 'B'){
            return WHITE | BISHOP;
        } else if(piece == 'R'){
            return WHITE | ROOK;
        } else if(piece == 'Q'){
            return WHITE | QUEEN;
        } else if(piece == 'K'){
            return WHITE | KING;
        }
        throw new IllegalArgumentException("Invalid piece representation: " + piece);
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
