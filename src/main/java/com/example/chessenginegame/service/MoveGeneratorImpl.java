package com.example.chessenginegame.service;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.PieceUtil;
import com.example.chessenginegame.util.TileUtil;

import java.util.*;

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
        HashMap<Integer, Pin> pieceIdToPinMap = getPieceIdToPinMap(board, color);
        List<King> kings = new ArrayList<>();
        for(Piece piece : pieces){
            if(!(piece instanceof King)) {
                moves.addAll(generateMovesFor(piece, board, pieceIdToPinMap.get(piece.getId())));
            } else{
                kings.add((King)piece);
            }
        }
        kings.forEach(king -> moves.addAll(generateKingMoves(king, board, moves)));
        return moves;
    }

    /**
     * @param piece   The piece to generate moves for
     * @param board   The current board state
     * @param pin     The pin involving the current piece, if exists
     * @return A list of all the legal moves for the provided piece in the current board state
     */
    public List<Move> generateMovesFor(Piece piece, Board board, Pin pin){
        if(piece instanceof Pawn){
            return generatePawnMoves((Pawn)piece, board, pin);
        } else if(piece instanceof Knight){
            return generateKnightMoves(piece, board, pin);
        } else if(piece instanceof Bishop ||
                  piece instanceof Rook ||
                  piece instanceof Queen){
            return generateSlidingPieceMoves(piece, board, pin);
        }
        return Collections.emptyList();
    }
    public List<Move> generatePawnMoves(Pawn pawn, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        int directionMultiplier = Pawn.getDirectionMultiplier(pawn.getColor());
        int currentTile = pawn.getTile();

        int leftCaptureDirection = 9 * directionMultiplier;
        int rightCaptureDirection = 7 * directionMultiplier;
        int pushDirection = 8 * directionMultiplier;
        if(isDiagonalCaptureValid(pawn, board, pin, leftCaptureDirection)){
            moves.add(new PawnMove(pawn, currentTile + leftCaptureDirection, true));
        }
        if(isDiagonalCaptureValid(pawn, board, pin, rightCaptureDirection)){
            moves.add(new PawnMove(pawn, currentTile + rightCaptureDirection, true));
        }
        if(pin == null || Math.abs(pin.direction) % 8 == 0){
            int pushEndTile = currentTile + pushDirection;
            if(board.getPieceAt(currentTile + pushDirection).isEmpty()){
                moves.add(new PawnMove(pawn, currentTile + pushDirection, false));
                if(!pawn.hasMoved() && board.getPieceAt(pushEndTile + pushDirection).isEmpty()){
                    moves.add(new PawnMove(pawn, pushEndTile + pushDirection, false));
                }
            }
        }
        return moves;
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
    public List<Move> generateSlidingPieceMoves(Piece piece, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        if(pin == null){
            for(int direction : piece.getMoveShifts()){
                moves.addAll(getMovesFromTileToEdgeOfBoard(piece, board, direction));
            }
        } else if(piece.getMoveShifts().contains(pin.direction)) {
            moves.addAll(getMovesFromTileToEdgeOfBoard(piece, board, pin.direction));
        }
        return moves;
    }
    public List<Move> generateKingMoves(Piece piece, Board board, List<Move> allNonKingMoves){
        List<Integer> possibleEndTiles = new ArrayList<>(piece.getMoveShifts().stream().
                map(moveShift -> moveShift + piece.getTile()).
                filter(TileUtil::isInBoard).
                filter(currentTile -> board.getPieceAt(currentTile).
                        map(occupant ->
                                !occupant.getColor().equals(piece.getColor())).
                        orElse(true)).toList());

        for(Move move : allNonKingMoves){
            if(possibleEndTiles.contains(move.getEndTile())){
                possibleEndTiles.remove(Integer.valueOf(move.getEndTile()));
            }
        }
        Optional<King> optionalOtherKing = board.getKing(PieceUtil.getOppositeColor(piece.getColor()));
        if(optionalOtherKing.isPresent()){
            King otherKing = optionalOtherKing.get();
            List<Integer> otherKingEndTiles = otherKing.getMoveShifts().stream().
                    map(moveShifts -> moveShifts + otherKing.getTile()).toList();

            for(int otherKingEndTile : otherKingEndTiles){
                if(possibleEndTiles.contains(otherKingEndTile)){
                    possibleEndTiles.remove(Integer.valueOf(otherKingEndTile));
                }
            }
        }

        return possibleEndTiles.stream().map(endTile -> new Move(piece, endTile)).toList();
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
        Optional<King> optionalKing = board.getKing(color);
        if(optionalKing.isEmpty()){
            return Collections.emptyList();
        }
        King king = optionalKing.get();
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
            return previousMove.getEndTile() == resultantTile + (-8 * Pawn.getDirectionMultiplier(piece.getColor()));
        }
        Piece occupant = endTile.get();
        return !occupant.getColor().equals(piece.getColor());
    }
}
