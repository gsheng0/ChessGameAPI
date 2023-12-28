package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.util.Constants;
import org.apache.tomcat.util.bcel.Const;

public abstract class Piece implements Comparable<Piece>{
    private static int counter = 0;
    private String color;
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
