package com.example.chessenginegame.model.piece;

import com.example.chessenginegame.util.Constants;

import java.util.List;

public abstract class Piece{
    private static int counter = 0;
    private int tile;
    private String color;
    private int id;
    private boolean hasMoved = false;
    public Piece(String color, int tile){
        this.color = color;
        this.tile = tile;
        this.id = counter;
        counter++;
    }
    private Piece(){}
    public int getTile() { return tile;}
    public String getColor() { return color; }
    public int getId() { return id; }
    public boolean hasMoved() { return hasMoved; }
    public abstract List<Integer> getMoveShifts();

    /**
     *
     * @param c character representation of a piece
     * @param tile the tile of the piece to be built
     * @return a piece object
     * @throws IllegalArgumentException if character is not a valid piece
     */
    public static Piece buildFromCharacter(char c, int tile){
        PieceBuilder pieceBuilder = new PieceBuilder();
        pieceBuilder.on(tile);
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
        private int tile;
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
        public PieceBuilder on(int tile){
            this.tile = tile;
            return this;
        }
        public Piece build(){
            Piece piece;
            if (pieceType.equals(Pawn.class)) {
                piece = new Pawn(color, tile);
            } else if (pieceType.equals(Knight.class)) {
                piece = new Knight(color, tile);
            } else if (pieceType.equals(Bishop.class)) {
                piece = new Bishop(color, tile);
            } else if (pieceType.equals(Rook.class)) {
                piece = new Rook(color, tile);
            } else if (pieceType.equals(Queen.class)) {
                piece = new Queen(color, tile);
            } else if (pieceType.equals(King.class)) {
                piece = new King(color, tile);
            } else {
                throw new IllegalArgumentException("Really not sure how you got this error");
            }
            return piece;
        }

    }
}
