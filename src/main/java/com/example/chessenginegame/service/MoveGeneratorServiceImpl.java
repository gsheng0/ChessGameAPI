package com.example.chessenginegame.service;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.PieceUtil;
import com.example.chessenginegame.util.TileUtil;
import com.example.chessenginegame.util.Tuple;

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

    //TODO: Look into attack/defend maps

    /**
     * @param board The current board state
     * @param color The color to generate moves for
     * @return a list of legal moves
     */
    @Override
    public List<Move> generateLegalMoves(Board board, String color){
        List<Tuple<Piece, Integer>> attackers = getAttackersOnKingOfColor(board, color);
        if(attackers.size() == 0){
            return generateLegalMovesNotInCheck(board, color);
        }
        else{
            int kingTile = board.getTileOfKing(color).orElseThrow(() -> new IllegalStateException("King for " + color + " not found"));
            return generateLegalMovesInCheck(board, attackers, color, kingTile);
        }
    }
    /**
     * @param board The current board state
     * @param color The side to generate moves for
     * @return A list of legal moves, disregarding check
     */
    public List<Move> generateLegalMovesNotInCheck(Board board, String color){
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
            moves.addAll(generateKingMoves1(kingTuple.getFirst(), kingTuple.getSecond(), board));//, opposingMoves));
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
            if(isDiagonalCaptureValid(pawn, currentTile, board, pin, captureDirection)){
                moves.add(new PawnMove(pawn, currentTile, currentTile + captureDirection, true));
            }
        }
        if(pin == null || Math.abs(pin.direction) % 8 == 0){
            int pushEndTile = currentTile + pushDirection;
            if(board.getPieceAt(pushEndTile).isEmpty()){
                moves.add(new PawnMove(pawn, currentTile, pushEndTile, false));
                if(TileUtil.isOnStartingRank(currentTile, pawn.getColor()) && board.getPieceAt(pushEndTile + pushDirection).isEmpty()){
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
    public List<Move> generateKingMoves1(Piece piece, int currentTile, Board board){
        String oppositeColor = PieceUtil.getOppositeColor(piece.getColor());
        List<Integer> possibleEndTiles = new ArrayList<>(King.moveShifts(currentTile).stream().
                map(moveShift -> moveShift + currentTile).
                filter(TileUtil::isInBoard).
                filter(tile -> board.getPieceAt(tile).
                        map(occupant ->
                                !occupant.getColor().equals(piece.getColor())).
                        orElse(true)).
                filter(tile -> !isTileProtectedBy(board, tile, oppositeColor)).toList());


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

            for(int i = 1; i < TileUtil.tilesToEdgeOfBoard(kingTile, direction); i++){ //while the tile to check is still on the board
                int currentTile = kingTile + direction * i;
                Optional<Piece> tile = board.getPieceAt(currentTile);
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
                    if(encountered instanceof SlidingPiece slidingPiece &&
                            slidingPiece.getMoveShifts().contains(-1 * direction) &&
                            !slidingPiece.getColor().equals(color)){
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
     * @return a list of attackers on the king
     */
    public List<Tuple<Piece, Integer>> getAttackersOnKingOfColor(Board board, String color){
        Optional<Integer> optionalKingTile = board.getTileOfKing(color);
        if(optionalKingTile.isEmpty()){
            return Collections.emptyList();
        }
        int kingTile = optionalKingTile.get();
        return getPiecesProtectingSquare(board, kingTile, PieceUtil.getOppositeColor(color), true);
    }

    /**
     *
     * @param board The current board state
     * @param tile The tile to be checked
     * @param color The color of the pieces to look for
     * @return a list of tuples containing the pieces protecting given tile and their tile numbers
     */
    public List<Tuple<Piece, Integer>> getPiecesProtectingSquare(Board board, int tile, String color, boolean includePinnedPieces){
        List<Tuple<Piece, Integer>> defenders = new ArrayList<>();

        //checking for knights
        List<Integer> moveShifts = Knight.moveShifts(tile);
        for(int moveShift : moveShifts){
            int resultantTile = moveShift + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Knight && occupant.getColor().equals(color)){
                defenders.add(Tuple.of(occupant, resultantTile));
            }
        }
        //checking for sliding pieces
        moveShifts = Queen.moveShifts();
        for(int direction : moveShifts){
            int tilesInDirection = TileUtil.tilesToEdgeOfBoard(tile, direction);
            int resultantTile = tile;
            for(int i = 0; i < tilesInDirection; i++){
                resultantTile += direction;
                Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
                if(optionalOccupant.isEmpty()){
                    continue;
                }
                Piece occupant = optionalOccupant.get();
                if(occupant instanceof SlidingPiece slidingPiece &&
                        slidingPiece.getMoveShifts().contains(-1 * direction) &&
                        slidingPiece.getColor().equals(color)){
                    defenders.add(Tuple.of(slidingPiece, resultantTile));
                }
                break;
            }
        }

        //check for pawns
        List<Integer> captureDirections = Pawn.captureDirections(tile, color);
        for(int captureDirection : captureDirections){
            int resultantTile = captureDirection + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Pawn){
                defenders.add(Tuple.of(occupant, resultantTile));
            }
        }

        if(includePinnedPieces){
            HashMap<Integer, Pin> pieceIdToPinMap = getPieceIdToPinMap(board, color);
            return defenders.stream().
                    filter(defenderInfo -> !pieceIdToPinMap.containsKey(defenderInfo.getFirst().getId())).toList();
        }
        return defenders;
    }

    /**
     *
     * @param board the current board state
     * @param tile the tile to be checked
     * @param color the color of the pieces to check
     * @return true if the tile is covered by any piece of the specified color
     */
    public boolean isTileProtectedBy(Board board, int tile, String color){
        //checking for knights
        List<Integer> moveShifts = Knight.moveShifts(tile);

        for(int moveShift : moveShifts){
            int resultantTile = moveShift + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Knight){
                return true;
            }
        }
        //checking for sliding pieces
        moveShifts = Queen.moveShifts();
        for(int direction : moveShifts){
            int tilesInDirection = TileUtil.tilesToEdgeOfBoard(tile, direction);
            int resultantTile = tile;
            for(int i = 0; i < tilesInDirection; i++){
                resultantTile += direction;
                Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
                if(optionalOccupant.isEmpty()){
                    continue;
                }
                Piece occupant = optionalOccupant.get();
                if(occupant instanceof SlidingPiece slidingPiece && slidingPiece.getMoveShifts().contains(-1 * direction)){
                    return true;
                }
            }
        }

        //check for pawns
        List<Integer> captureDirections = Pawn.captureDirections(tile, color);
        for(int captureDirection : captureDirections){
            int resultantTile = captureDirection + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Pawn){
                return true;
            }
        }
        return false;
    }

    /**
     * @param board the current board state
     * @param attackers list of pieces threatening the king and their tile numbers
     * @param color the color of the king being threatened
     * @return a list of legal moves for defending the king from the check
     */
    public List<Move> generateLegalMovesInCheck(Board board, List<Tuple<Piece, Integer>> attackers, String color, int kingTile){
        List<Move> moves = new ArrayList<>();
        String oppositeColor = PieceUtil.getOppositeColor(color);
        Optional<Integer> optionalKingTile = board.getTileOfKing(color);
        if(optionalKingTile.isEmpty()){
            throw new IllegalStateException(color + " does not have a king");
        }
        King king = (King)board.getBoard().get(optionalKingTile.get());
        if(attackers.size() == 0){
            throw new IllegalStateException(color + " is not in check");
        }
        //generate legal king moves first
        List<Integer> moveShifts = King.moveShifts(kingTile);
        for(int moveShift : moveShifts){
            int resultantTile = moveShift + kingTile;
            if(!TileUtil.isInBoard(resultantTile)){
                continue;
            }
            if(isTileProtectedBy(board, resultantTile, oppositeColor)){
                continue;
            }
            if(board.getPieceAt(resultantTile).isPresent() && board.getPieceAt(resultantTile).get().getColor().equals(color)){
                continue;
            }
            moves.add(new Move(king, kingTile, resultantTile));
        }
        if(attackers.size() == 2 ){
            return moves;
        }
        Tuple<Piece, Integer> attackerInfo = attackers.get(0);
        Piece attacker = attackerInfo.getFirst();
        if(attacker instanceof Knight || attacker instanceof Pawn){
            int attackerTile = attackerInfo.getSecond();

           List<Tuple<Piece, Integer>> defendersInfo = getPiecesProtectingSquare(board, attackerTile, color, false);
           for(Tuple<Piece, Integer> defenderInfo : defendersInfo){
               moves.add(new Move(defenderInfo.getFirst(), defenderInfo.getSecond(), attackerTile));
           }

           return moves;
        }
        else if(attacker instanceof SlidingPiece){
            int attackerTile = attackerInfo.getSecond();
            int attackerDirection = TileUtil.getSlidingDirection(attackerTile, kingTile);
            int distance = (kingTile - attackerTile)/attackerDirection;
            for(int i = 0; i < distance + 1; i++){
                int currentTile = attackerTile + i * attackerDirection;
                List<Tuple<Piece, Integer>> defendersInfo = getPiecesProtectingSquare(board, currentTile, color, false);
                for(Tuple<Piece, Integer> defenderInfo : defendersInfo){
                    moves.add(new Move(defenderInfo.getFirst(), defenderInfo.getSecond(), attackerTile));
                }
            }
            return moves;
        }
        System.out.println("Not sure how you got here, but Congratulations");
        return moves;
    }
}
