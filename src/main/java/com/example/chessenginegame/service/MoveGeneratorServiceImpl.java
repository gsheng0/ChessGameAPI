package com.example.chessenginegame.service;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.Constants;
import com.example.chessenginegame.util.PieceUtil;
import com.example.chessenginegame.util.TileUtil;
import com.example.chessenginegame.util.Tuple;
import org.apache.tomcat.util.bcel.Const;

import java.util.*;

//TODO: Check for checks
/*
Ways to get out of check:
1. Move king to unprotected square
2. Capture opposing piece
3. Block check with your own piece

If double check:
1. Move king to unprotected square

 */
public class MoveGeneratorServiceImpl implements MoveGeneratorService {
    /**
     * @param board The current board state
     * @param color The side to generate moves for
     * @return A list of legal moves
     */
    //TODO: Look into attack/defend maps
    @Override
    public List<Move> generateLegalMoves(Board board, String color){
        List<Move> moves = new ArrayList<>();
        List<Move> opposingMoves = new ArrayList<>();
        List<Integer> pieceTiles = board.getPieceTiles(color);
        HashMap<Integer, Pin> pieceIdToPinMap = getPieceIdToPinMap(board, color);
        Tuple<King, Integer> kingTuple = null;
        for(int tile : pieceTiles){
            Piece piece = board.getPieceAt(tile).orElseThrow(() -> new RuntimeException("Piece does not exist on tile " + tile));
            if(!(piece instanceof King)){
                if(piece.getColor().equals(color)) {
                    moves.addAll(generateMovesFor(piece, tile, board, pieceIdToPinMap.get(piece.getId())));
                }
                else{
                    opposingMoves.addAll(generateMovesFor(piece, tile, board, null));
                }
            } else if(piece.getColor().equals(color)){
                kingTuple = Tuple.of((King)piece, tile);
            }
        }
        if(kingTuple != null){
            moves.addAll(generateKingMoves(kingTuple.getFirst(), kingTuple.getSecond(), board, opposingMoves));
        }
        return moves;
    }

    /**
     * @param piece   The piece to generate moves for
     * @param currentTile    The tile that the piece is on
     * @param board   The current board state
     * @param pin     The pin involving the current piece, if exists
     * @return A list of all the legal moves for the provided piece in the current board state
     */
    public List<Move> generateMovesFor(Piece piece, int currentTile, Board board, Pin pin){
        if(piece instanceof Pawn){
            return generatePawnMoves((Pawn)piece, currentTile, board, pin);
        } else if(piece instanceof Knight){
            return generateKnightMoves(piece, currentTile, board, pin);
        } else if(piece instanceof SlidingPiece){
            return generateSlidingPieceMoves((SlidingPiece) piece, currentTile, board, pin);
        }
        return Collections.emptyList();
    }

    /**
     * @param pawn The pawn to be generating moves for
     * @param currentTile The tile that the pawn is sitting on
     * @param board The current board state
     * @param pin  The pin involving the current pawn, if exists
     * @return a list of legal moves for this pawn
     */
    public List<Move> generatePawnMoves(Pawn pawn, int currentTile, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        int directionMultiplier = Pawn.getDirectionMultiplier(pawn.getColor());

        List<Integer> captureDirections = Pawn.captureDirections(currentTile, pawn.getColor());
        int pushDirection = 8 * directionMultiplier;
        for(int captureDirection : captureDirections){
            moves.add(new PawnMove(pawn, currentTile, currentTile + captureDirection, true));
        }
        if(pin == null || Math.abs(pin.direction) % 8 == 0){
            int pushEndTile = currentTile + pushDirection;
            if(board.getPieceAt(currentTile + pushDirection).isEmpty()){
                moves.add(new PawnMove(pawn, currentTile, currentTile + pushDirection, false));
                if(!pawn.hasMoved() && board.getPieceAt(pushEndTile + pushDirection).isEmpty()){
                    moves.add(new PawnMove(pawn, currentTile, pushEndTile + pushDirection, false));
                }
            }
        }
        return moves;
    }

    /**
     * @param piece the piece to be generating moves for
     * @param currentTile the tile of the current piece
     * @param board the current board state
     * @param pin the pin involving the current piece, if exists
     * @return a list of legal knight moves
     */
    public List<Move> generateKnightMoves(Piece piece, int currentTile, Board board, Pin pin){
        if(pin != null){
            return Collections.emptyList();
        }
        return Knight.moveShifts(currentTile).stream().
                map(moveShift -> moveShift + currentTile).
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
                map(tile -> new Move(piece, currentTile, tile)).toList();

    }

    /**
     * @param slidingPiece The piece to be generating moves for
     * @param currentTile The tile the piece is currently on
     * @param board The current board state
     * @param pin The pin involving the current piece, if exists
     * @return A list of legal moves for the piece provided
     */
    public List<Move> generateSlidingPieceMoves(SlidingPiece slidingPiece, int currentTile, Board board, Pin pin){
        List<Move> moves = new ArrayList<>();
        if(pin == null){
            for(int direction : slidingPiece.getMoveShifts()){
                moves.addAll(getMovesFromTileToEdgeOfBoard(slidingPiece, currentTile, board, direction));
            }
        } else if(slidingPiece.getMoveShifts().contains(pin.direction)) {
            moves.addAll(getMovesFromTileToEdgeOfBoard(slidingPiece, currentTile, board, pin.direction));
        }
        return moves;
    }

    /**
     * @param piece The king to be generating moves for
     * @param currentTile The tile that the king is on
     * @param board The current board state
     * @param allNonKingMoves a list of non King moves by the opposing side
     * @return a list of legal king moves
     */
    public List<Move> generateKingMoves(Piece piece, int currentTile, Board board, List<Move> allNonKingMoves){
        List<Integer> possibleEndTiles = new ArrayList<>(King.moveShifts(currentTile).stream().
                map(moveShift -> moveShift + currentTile).
                filter(TileUtil::isInBoard).
                filter(tile -> board.getPieceAt(tile).
                        map(occupant ->
                                !occupant.getColor().equals(piece.getColor())).
                        orElse(true)).toList());

        for(Move move : allNonKingMoves){
            if(move.getPiece().getColor().equals(piece.getColor())){
                continue;
            }
            if(possibleEndTiles.contains(move.getEndTile())){
                if(move instanceof PawnMove pawnMove){
                    if(!pawnMove.isCapture()){
                        continue;
                    }
                }
                possibleEndTiles.remove(Integer.valueOf(move.getEndTile()));
            }
        }
        Optional<Integer> optionalOtherKingTile = board.getTileOfKing(PieceUtil.getOppositeColor(piece.getColor()));
        if(optionalOtherKingTile.isPresent()){
            int otherKingTile = optionalOtherKingTile.get();
            List<Integer> otherKingEndTiles = King.moveShifts(currentTile).stream().
                    map(moveShifts -> moveShifts + otherKingTile).toList();

            for(int otherKingEndTile : otherKingEndTiles){
                if(possibleEndTiles.contains(otherKingEndTile)){
                    possibleEndTiles.remove(Integer.valueOf(otherKingEndTile));
                }
            }
        }

        return possibleEndTiles.stream().map(endTile -> new Move(piece, currentTile, endTile)).toList();
    }

    /**
     *
     * @param piece The piece to be generated moves for
     * @param currentTile The tile the piece is on
     * @param board The current board state
     * @param direction The direction to check
     * @return A list of valid moves going in that direction
     */
    public List<Move> getMovesFromTileToEdgeOfBoard(Piece piece, int currentTile, Board board, int direction){
        List<Move> moves = new ArrayList<>();
        int numTiles = TileUtil.tilesToEdgeOfBoard(currentTile, direction);
        int nextTile = currentTile;
        for(int i = 0; i < numTiles; i++){
            nextTile += direction;
            Optional<Piece> optionalPiece = board.getPieceAt(nextTile);
            if(optionalPiece.isEmpty()){
                moves.add(new Move(piece, currentTile, nextTile));
            } else {
                Piece occupant = optionalPiece.get();
                if(!occupant.getColor().equals(piece.getColor())){
                    moves.add(new Move(piece,currentTile, nextTile));
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
        Optional<Integer> optionalKingTile = board.getTileOfKing(color);
        if(optionalKingTile.isEmpty()){
            return Collections.emptyList();
        }
        int kingTile = optionalKingTile.get();
        List<Integer> directions = Queen.moveShifts();
        List<Pin> pins = new ArrayList<>();
        for(int direction : directions){
            Piece prevEncountered = null;
            int currentTile = kingTile + direction;
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
                    if(encountered instanceof SlidingPiece slidingPiece && slidingPiece.getMoveShifts().contains(-1 * direction)){
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
    public boolean isDiagonalCaptureValid(Piece piece, int currentTile, Board board, Pin pin, int direction){
        int resultantTile = currentTile + direction;
        Optional<Piece> endTile = board.getPieceAt(currentTile + direction);
        if(pin != null && pin.direction != direction){//if pin exists and directions do not match, then pawn cannot move
            return false;
        }

        if(endTile.isEmpty()){ //no piece on the resultant tile
            Optional<Move> optionalMove = board.getPreviousMove();
            if(optionalMove.isEmpty()){ //no previous move
                return false;
            }
            if(!TileUtil.isOnRankForEnPassant(currentTile, piece.getColor())){ //not on right rank for en passant
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

    /**
     * @param board the current board state
     * @param color the color of the side to be checking
     * @return an integer representing the check status of the board
     */
    public int getCheckStatus(Board board, String color){
        int attackers = 0;
        Optional<Integer> optionalKingTile = board.getTileOfKing(color);
        if(optionalKingTile.isEmpty()){
            return Constants.NO_CHECK;
        }
        int kingTile = optionalKingTile.get();
        //checking for knights
        List<Integer> moveShifts = Knight.moveShifts(kingTile);
        for(int moveShift : moveShifts){
            int resultantTile = moveShift + kingTile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Knight){
                attackers++;
            }
        }
        //checking for sliding pieces
        moveShifts = Queen.moveShifts();
        for(int direction : moveShifts){
            int tilesInDirection = TileUtil.tilesToEdgeOfBoard(kingTile, direction);
            int resultantTile = kingTile;
            for(int i = 0; i < tilesInDirection; i++){
                resultantTile += direction;
                Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
                if(optionalOccupant.isEmpty()){
                    continue;
                }
                Piece occupant = optionalOccupant.get();
                if(occupant instanceof SlidingPiece slidingPiece && slidingPiece.getMoveShifts().contains(-1 * direction)){
                    attackers++;
                }
            }
        }

        //check for pawns
        int directionMultiplier = Pawn.getDirectionMultiplier(color);
        //check two diagonals
        //make sure it doesn't cross the side of the board

        if(attackers == 1){
            return Constants.CHECK;
        } else if(attackers == 2){
            return Constants.DOUBLE_CHECK;
        }
        return Constants.NO_CHECK;

    }
}
