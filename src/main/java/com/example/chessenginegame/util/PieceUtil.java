package com.example.chessenginegame.util;

public class PieceUtil {
    public static final int EMPTY = 0;
    public static final int PAWN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int QUEEN = 5;
    public static final int KING = 6;
    public static final int WHITE = 16;
    public static final int BLACK = 8;

    /**
     @param piece Integer representation of a piece
     @return A single character representing that piece. Uppercase for White, Lowercase for Black
     K for king, Q for queen, R for rook, B for bishop, N for Knight, and P for pawn
     @throws IllegalArgumentException if the piece parameter is not a valid piece
     */
    public static char convertIntegerPieceToCharacterPiece(int piece) {
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
    public static int convertCharPieceToIntegerPiece(char piece){
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

}
