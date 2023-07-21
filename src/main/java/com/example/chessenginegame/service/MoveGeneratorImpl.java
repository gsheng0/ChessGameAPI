package com.example.chessenginegame.service;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.TileUtil;

import java.util.*;
import java.util.stream.Stream;

public class MoveGeneratorImpl implements MoveGenerator {
    /**
     * @param board The current board state
     * @param color The side to generate moves for
     * @return A list of legal moves
     */
    @Override
    public List<Move> generateLegalMoves(Board board, String color){
        List<Move> moves = new ArrayList<>();
        List<Piece> pieces = board.getPieces(color);
        //HashMap<Integer, Piece> pieceIdToPinningPieceMap = getPieceIdToPinningPieceMap(board, color);
        HashMap<Integer, Pin> pieceIdToPinMap = getPieceIdToPinMap(board, color);
        for(Piece piece : pieces){
            moves.addAll(generateMovesFor(piece, board, pieceIdToPinMap.get(piece.getId())));
        }
        return moves;
    }

    /**
     * @param piece   The piece to generate moves for
     * @param board   The current board state
     * @param pin     The pin involving the current piece, if exists
     * @return A list of all the legal moves for the provided piece in the current board state
     */
    public List<Move> generateMovesFor(Piece piece, Board board, Pin pin){
        int tile = piece.getTile();
        String color = piece.getColor();
        if(piece instanceof Pawn){
            return generatePawnMoves(piece, board, pin);
        } else if(piece instanceof Knight){
            return generateKnightMoves(piece, board, pin);
        } else if(piece instanceof Bishop){
            return generateBishopMoves(piece, board, pin);
        } else if(piece instanceof Rook){
            return generateRookMoves(piece, board, pin);
        } else if(piece instanceof Queen){
            return generateQueenMoves(piece, board, pin);
        } else if(piece instanceof King){
            return generateKingMoves(piece, board, pin);
        }
        return Collections.emptyList();
    }
    public List<Move> generatePawnMoves(Piece piece, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        int directionMultiplier = Pawn.getDirectionMultiplier(piece.getColor());
        int currentTile = piece.getTile();
        Stream.of(7, 9).
                map(shift -> shift * directionMultiplier + currentTile).
                filter(tile -> {})

        int leftCapture = 9 * directionMultiplier + currentTile;
        int rightCapture = 7 * directionMultiplier + currentTile;
        int push = 8 * directionMultiplier + currentTile;




        return Collections.emptyList();
    }
    public List<Move> generateKnightMoves(Piece piece, Board board, Pin pin){
        if(pin != null){
            return Collections.emptyList();
        }
        return piece.getMoveShifts().stream().
                map(moveShift -> moveShift + piece.getTile()).
                filter(TileUtil::isInBoard).
                filter(tile -> {
                    if(board.getPieceAt(tile).isEmpty()){
                        return true;
                    }
                    if(board.getPieceAt(tile).get().getColor().equals(piece.getColor())){ //piece of same color
                        return false;
                    }
                    return true;
                }).
                map(tile -> new Move(piece, tile)).toList();

    }
    public List<Move> generateBishopMoves(Piece piece, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        for(int direction : piece.getMoveShifts()){
            moves.addAll(getMovesFromTileToEdgeOfBoard(piece, board, direction));
        }
        return moves;
    }
    public List<Move> generateRookMoves(Piece piece, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        for(int direction : piece.getMoveShifts()){
            moves.addAll(getMovesFromTileToEdgeOfBoard(piece, board, direction));
        }
        return moves;
    }
    public List<Move> generateQueenMoves(Piece piece, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        for(int direction : piece.getMoveShifts()){
            moves.addAll(getMovesFromTileToEdgeOfBoard(piece, board, direction));
        }
        return moves;
    }
    public List<Move> generateKingMoves(Piece piece, Board board, Pin pin){
        return piece.getMoveShifts().stream().
                map(moveShift -> moveShift + piece.getTile()).
                filter(TileUtil::isInBoard).
                filter(currentTile -> board.getPieceAt(currentTile).
                        map(occupant ->
                            !occupant.getColor().equals(piece.getColor())).
                        orElse(true)).
                filter(tile -> isTileProtectedByOppositeColor(tile, piece.getColor())).
                map(tile -> new Move(piece, tile)).toList();
    }

    /**
     *
     * @param piece The piece to be generated moves for
     * @param board The current board state
     * @param direction The direction to check
     * @return A list of valid moves going in that direction
     */
    public List<Move> getMovesFromTileToEdgeOfBoard(Piece piece, Board board, int direction){
        List<Move> moves = new ArrayList<>();
        int currentTile = piece.getTile();
        int numTiles = TileUtil.tilesToEdgeOfBoard(currentTile, direction);
        for(int i = 0; i < numTiles; i++){
            currentTile += direction;
            Optional<Piece> optionalPiece = board.getPieceAt(currentTile);
            if(optionalPiece.isEmpty()){
                moves.add(new Move(piece, currentTile));
            } else {
                Piece occupant = optionalPiece.get();
                if(!occupant.getColor().equals(piece.getColor())){
                    moves.add(new Move(piece, currentTile));
                }
                break;
            }
        }
        return moves;
    }

    public HashMap<Integer, Piece> getPieceIdToPinningPieceMap(Board board, String color){
        HashMap<Integer, Piece> pieceIdToPinningPiece = new HashMap<>();
        List<Pin> pins = getPins(board, color);
        for(Pin pin : pins){
            pieceIdToPinningPiece.put(pin.pinnedPiece.getId(), pin.pinningPiece);
        }
        return pieceIdToPinningPiece;
    }

    /**
     *
     * @param board Current board state
     * @param color The color for whose pins to look for
     * @return A map that maps pinned piece id to a pin object
     */
    public HashMap<Integer, Pin> getPieceIdToPinMap(Board board, String color){
        HashMap<Integer, Pin> pieceIdToPinMap = new HashMap<>();
        List<Pin> pins = getPins(board, color);
        for(Pin pin : pins){
            pieceIdToPinMap.put(pin.pinnedPiece.getId(), pin);
        }
        return pieceIdToPinMap;
    }

    /**
     * This method finds a list of pins by first finding the tile of the king, and looking in the
     * 8 directions, checking for bishops on the diagonals, rooks on the files and ranks, and queens on
     * both, returning only those with one piece in between them and the king
     * @param board The current board state
     * @param color The color the check for
     * @return A list of pins that exist on the board
     */
    public List<Pin> getPins(Board board, String color){
        King king = board.getKing(color).orElseThrow(() -> new RuntimeException("No king found for side: " + color));
        List<Integer> directions = Queen.moveShifts();
        List<Pin> pins = new ArrayList<>();
        for(int direction : directions){
            Piece prevEncountered = null;
            int currentTile = king.getTile() + direction;
            while(TileUtil.isInBoard(currentTile)){ //while the tile to check is still on the board
                Optional<Piece> tile = board.getPieceAt(currentTile);
                currentTile += direction;
                if(tile.isEmpty()){
                    continue;
                }
                Piece encountered = tile.get();
                if(prevEncountered == null){ //first piece
                    if (!encountered.getColor().equals(color)) { //color of first piece needs to match in order to be pinned
                        continue;
                    }
                    prevEncountered = encountered;
                } else { //second piece
                    if(encountered instanceof King ||
                        encountered instanceof Knight ||
                        encountered instanceof Pawn){
                        continue;
                    }
                    if(encountered.getMoveShifts().contains(-1 * direction)){
                        pins.add(new Pin(prevEncountered, encountered, direction));
                    }
                }
            }
        }
        return pins;
    }

    /**
     *
     * @param piece The pawn being checked
     * @param board The current board state
     * @param pin The pin involving the current pawn, if exists
     * @param direction The direction of the capture
     * @return true of the capture is valid
     */
    public boolean isDiagonalCaptureValid(Piece piece, Board board, Pin pin, int direction){
        int resultantTile = piece.getTile() + direction;
        Optional<Piece> endTile = board.getPieceAt(piece.getTile() + direction);
        if(pin != null && pin.direction != direction){//if pin exists and directions do not match, then pawn cannot move
            return false;
        }

        if(endTile.isEmpty()){ //no piece on the resultant tile
            Optional<Move> optionalMove = board.getPreviousMove();
            if(optionalMove.isEmpty()){ //no previous move
                return false;
            }
            if(!TileUtil.isOnRankForEnPassant(piece.getTile(), piece.getColor())){ //not on right rank for en passant
                return false;
            }
            Move previousMove = optionalMove.get();
            if(!(previousMove.getPiece() instanceof Pawn)){ //previous move was not a pawn move
                return false;
            }
            //if the previous move involves a pawn ending up on the tile behind the resultant tile
            return previousMove.getEnd() == resultantTile + (-8 * Pawn.getDirectionMultiplier(piece.getColor()));
        }
        Piece occupant = endTile.get();
        return !occupant.getColor().equals(piece.getColor());
    }
    public boolean isTileProtectedByOppositeColor(int tile, String color){
        return false;
    }
}
