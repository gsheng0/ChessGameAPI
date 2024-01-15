package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.util.TileUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece implements Comparable<Piece>{
    public static final int EMPTY = 0;
    public static final int PAWN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int QUEEN = 5;
    public static final int KING = 6;
    public static final int WHITE = 16;
    public static final int BLACK = 8;

    private static int counter = 0;

    protected String color;
    private int id;
    private boolean hasMoved = false;

    public Piece(String color){
        this.color = color;
        this.id = counter;
        counter++;
    }
    private Piece(){}

    public String getColor() { return color; }
    public int getId() { return id; }
    public boolean hasMoved() { return hasMoved; }
    public abstract String getName();
    public abstract int getValue();
    public abstract List<Integer> moveShifts(int tile);

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 0;

        result = prime * result + color.hashCode();
        result += prime * result + getName().hashCode();

        return result;
    }
    @Override
    public int compareTo(Piece other) {
        if(!this.getColor().equals(other.getColor())){
            return other.getColor().compareTo(this.getColor());
        }
        return Integer.compare(this.getValue(), other.getValue());
    }

    public abstract char toChar();
    public abstract String toAbv(); // return lowerCaseColor-UpperCaseChar


    public static List<Integer> straightMoveShifts(int tile) {
        List<Integer> ms = new ArrayList<>();
        int currFile = TileUtil.getFile(tile), currRank = TileUtil.getRank(tile);
        // up
        int nextRank = currRank + 1;
        int nextTile = tile + Constants.UP;
        while (nextRank <= Board.LENGTH) {
            ms.add(nextTile);
            nextRank ++;
            nextTile += Constants.UP;
        }
        // down
        nextRank = currRank - 1;
        nextTile = tile + Constants.DOWN;
        while (nextRank >= 1) {
            ms.add(nextTile);
            nextRank --;
            nextTile += Constants.DOWN;
        }
        // left
        int nextFile = currFile - 1;
        nextTile = tile + Constants.LEFT;
        while (nextFile >= 1) {
            ms.add(nextTile);
            nextFile --;
            nextTile += Constants.LEFT;
        }
        // right
        nextFile = currFile + 1;
        nextTile = tile + Constants.RIGHT;
        while (nextFile <= Board.LENGTH) {
            ms.add(nextFile);
            nextFile ++;
            nextTile += Constants.RIGHT;
        }
        return ms;
    }

    public List<Integer> diagMoveShifts(int tile) {
        int currFile = TileUtil.getFile(tile), currRank = TileUtil.getRank(tile);
        List<Integer> moveShifts = new ArrayList<>();
        // right-up
        int nextFile = currFile + 1;
        int nextRank = currRank + 1;
        int nextTile = tile + Constants.RIGHT_UP;
        while (nextFile <= Board.LENGTH  && nextRank <= Board.LENGTH) {
            moveShifts.add(nextTile);
            nextFile ++;
            nextRank ++;
            nextTile += Constants.RIGHT_UP;
        }
        // left-up
        nextFile = currFile - 1;
        nextRank = currRank + 1;
        nextTile = tile + Constants.LEFT_UP;
        while (nextFile >= 1  && nextRank <= Board.LENGTH) {
            moveShifts.add(nextTile);
            nextFile --;
            nextRank ++;
            nextTile += Constants.LEFT_UP;
        }
        // left-down
        nextFile = currFile - 1;
        nextRank = currRank - 1;
        nextTile = tile + Constants.LEFT_DOWN;
        while (nextFile >= 1  && nextRank >= 1) {
            moveShifts.add(nextTile);
            nextFile --;
            nextRank --;
            nextTile += Constants.LEFT_DOWN;
        }
        // right-down
        nextFile = currFile + 1;
        nextRank = currRank - 1;
        nextTile = tile + Constants.RIGHT_DOWN;
        while (nextFile <= Board.LENGTH  && nextRank >= 1) {
            moveShifts.add(nextTile);
            nextFile ++;
            nextRank --;
            nextTile += Constants.RIGHT_DOWN;
        }
        return moveShifts;
    }
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

    public static String getOppositeColor(String color){
        if(color.equals(Constants.WHITE)){
            return Constants.BLACK;
        }
        else if(color.equals(Constants.BLACK)){
            return Constants.WHITE;
        }
        return "";
    }

    public static Piece of(String s){
        return of(s.charAt(0));
    }

    /**
     *
     * @param c character representation of a piece
     * @return a piece object
     * @throws IllegalArgumentException if character is not a valid piece
     */
    public static Piece of(char c){
        PieceBuilder pieceBuilder = new PieceBuilder();
        if(c == 'p'){
            return pieceBuilder.black().pawn().build();
        } else if(c == 'n'){
            return pieceBuilder.black().knight().build();
        } else if(c == 'b'){
            return pieceBuilder.black().bishop().build();
        } else if(c == 'r'){
            return pieceBuilder.black().rook().build();
        } else if(c == 'q'){
            return pieceBuilder.black().queen().build();
        } else if(c == 'k'){
            return pieceBuilder.black().king().build();
        } else if(c == 'P'){
            return pieceBuilder.white().pawn().build();
        } else if(c == 'N'){
            return pieceBuilder.white().knight().build();
        } else if(c == 'B'){
            return pieceBuilder.white().bishop().build();
        } else if(c == 'R'){
            return pieceBuilder.white().rook().build();
        } else if(c == 'Q'){
            return pieceBuilder.white().queen().build();
        } else if(c == 'K'){
            return pieceBuilder.white().king().build();
        }
        throw new IllegalArgumentException("Invalid piece representation: " + c);
    }
    public static class PieceBuilder{
        private String color;
        private Class<? extends Piece> pieceType;
        public PieceBuilder black(){
            this.color = Constants.BLACK;
            return this;
        }
        public PieceBuilder white(){
            this.color = Constants.WHITE;
            return this;
        }
        public PieceBuilder pawn(){
            pieceType = Pawn.class;
            return this;
        }
        public PieceBuilder knight(){
            pieceType = Knight.class;
            return this;
        }
        public PieceBuilder bishop(){
            pieceType = Bishop.class;
            return this;
        }
        public PieceBuilder rook(){
            pieceType = Rook.class;
            return this;
        }
        public PieceBuilder queen(){
            pieceType = Queen.class;
            return this;
        }
        public PieceBuilder king(){
            pieceType = King.class;
            return this;
        }
        public Piece build(){
            Piece piece;
            if (pieceType.equals(Pawn.class)) {
                piece = new Pawn(color);
            } else if (pieceType.equals(Knight.class)) {
                piece = new Knight(color);
            } else if (pieceType.equals(Bishop.class)) {
                piece = new Bishop(color);
            } else if (pieceType.equals(Rook.class)) {
                piece = new Rook(color);
            } else if (pieceType.equals(Queen.class)) {
                piece = new Queen(color);
            } else if (pieceType.equals(King.class)) {
                piece = new King(color);
            } else {
                throw new IllegalArgumentException("Really not sure how you got this error");
            }
            return piece;
        }
    }
}
