package com.example.chessenginegame.model;

import com.example.chessenginegame.model.piece.King;
import com.example.chessenginegame.model.piece.Piece;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {
    private Map<Integer, Piece> indexPieceMap;
    private String nextMoveColor;
    private Move previousMove;
    public static Integer MIN_TILE = 0;
    public static Integer MAX_TILE = 63;
    public static Integer LENGTH = 8;

//    public Board(){  }
//    public Board(Map<Integer, Piece> indexPieceMap){
//        this.indexPieceMap = indexPieceMap;
//    }
    public Board(Map<Integer, Piece> indexPieceMap, @NonNull String nextMoveColor){
        this.indexPieceMap = indexPieceMap;
        this.nextMoveColor = nextMoveColor;
    }
    public Board(Map<Integer, Piece> indexPieceMap, Move previousMove){
        this.indexPieceMap = indexPieceMap;
        this.previousMove = previousMove;
        this.nextMoveColor = Piece.getOppositeColor(this.previousMove.getPiece().getColor());
    }
    public void printBoardMatrix() {
        System.out.println();
        System.out.println("F |  0     1     2     3     4     5     6     7    |");
        System.out.println("-------------------------------------------------------");
        String[][] boardMatrix = getBoardAsMatrix();
        for (int r=0; r<LENGTH; r++) {
            System.out.print(r + " | ");
            for (int c=0; c<LENGTH; c++) {
                if (boardMatrix[r][c] == null) {
                    System.out.printf("%2s__  ", (r*8 +c));
                } else {
                    System.out.printf("%2s%s  ", (r*8 + c), boardMatrix[r][c]);
                }
            }
            System.out.println("| " + (LENGTH-r));
        }
        System.out.println("-------------------------------------------------------");
        System.out.println("R |  a     b     c     d     e     f     g     h    |");
        System.out.println();
    }
    public static void printBoard() {
        System.out.println();
        System.out.println("  F|  0  1  2  3  4  5  6  7 |");
        System.out.println("--------------------------------");
        for (int r=0; r<LENGTH; r++) {
            System.out.print(r + "  |");
            for (int c=0; c<LENGTH; c++) {
                System.out.printf(" %2s", String.valueOf(r * 8 + c));
            }
            System.out.println(" |  " + (LENGTH-r));
        }
        System.out.println("---------------------------------");
        System.out.println("R  |  a  b  c  d  e  f  g  h |");
        System.out.println();
    }
    public String[][] getBoardAsMatrix() {
        String[][] boardMatrix = new String[LENGTH][LENGTH];
        for (Integer key : indexPieceMap.keySet()) {
            int row = key / LENGTH ;
            int col = key % LENGTH ;
            Piece piece = indexPieceMap.get(key);
            boardMatrix[row][col] = piece.toAbv();
        }
        return boardMatrix;
    }
    /**
     * @param move The move to be applied to the board
     * @return a copy of the board, with the move applied
     */
    public Board apply(Move move){
        if(!indexPieceMap.containsKey(move.getStartTile())){
            throw new IllegalArgumentException("Move not applicable to board: missing piece on start tile");
        }
        Map<Integer, Piece> newBoard = copyBoard(indexPieceMap);
        Piece piece = newBoard.remove(move.getStartTile());
        newBoard.put(move.getEndTile(), piece);
        return new Board(newBoard, move);
    }
    public Board apply(List<Move> moves) {
        Board current = this;
        for(Move move : moves){
            current = current.apply(move);
        }
        return current;
    }
    public Board apply(String uci){
        return apply(Move.parseUCIMove(this, uci));
    }
    public Board applyUciMoves(List<String> uciMoves) {
        Board current = this;
        for (String uciMove : uciMoves) {
            current = current.apply(uciMove);
        }
        return current;
    }
    public Optional<Piece> getPieceAt(int tile){
        if(!indexPieceMap.containsKey(tile)){
            return Optional.empty();
        }
        return Optional.of(indexPieceMap.get(tile));
    }
    public List<Integer> getPieceTiles(String color){
        return indexPieceMap.keySet().stream().filter(tile -> indexPieceMap.get(tile).getColor().equals(color)).toList();
    }
    public Optional<Integer> getTileOfKing(String color){
        //TODO: optional needed? as it's over if the king not there
        for(int tile : indexPieceMap.keySet()){
            Piece piece = indexPieceMap.get(tile);
            if(!(piece instanceof King)){
                continue;
            }
            if(piece.getColor().equals(color)){
                return Optional.of(tile);
            }
        }
        return Optional.empty();
    }
    public static Board startingPosition() {
        return new Board(Board.createFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"), Constants.WHITE);
    }
    public Optional<Move> getPreviousMove(){
        return Optional.ofNullable(previousMove);
    }
    public String getNextMoveColor() { return nextMoveColor; }
    public Map<Integer, Piece> getIndexPieceMap(){
        return indexPieceMap;
    }
    public static Map<Integer, Piece> createFromFEN(String FEN){
        Map<Integer, Piece> indPieceMap = new HashMap<>();
        int index = 0;
        for(int i = 0; i < FEN.length(); i++){
            if(index > MAX_TILE){
                break;
            }
            char current = FEN.charAt(i);
            if(current == '/'){
                continue;
            }
            if(current > '0' && current < '9'){
                int num = current - '0';
                index += num;
            }
            else{
                indPieceMap.put(index, Piece.of(current));
                index++;
            }
        }
        return indPieceMap;
    }
    private static Map<Integer, Piece> copyBoard(Map<Integer, Piece> board){
        Map<Integer, Piece> copy = new HashMap<>();
        board.keySet().forEach(tile->copy.put(tile, board.get(tile)));
        return copy;
    }

}
