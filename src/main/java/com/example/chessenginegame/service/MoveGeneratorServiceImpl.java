package com.example.chessenginegame.service;

import com.example.chessenginegame.model.*;
import com.example.chessenginegame.model.piece.*;
import com.example.chessenginegame.util.TileUtil;
import com.example.chessenginegame.util.Pair;

import java.util.*;

//TODO: Write unit tests for each of the functions in this class
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
//     * @param color The side to generate moves for
     * @param depth The depth to gnereate
     * @return the root MoveTreeNode, which has null move and null dad and only kids
     */
    public MoveTreeNode generateLegalMovesTree(MoveTreeNode root, Board board, int depth) {
        if (depth == 0) {
            return root;
        }
        if (root == null) {
            root = new MoveTreeNode();
        }
        List<Move> moves = generateLegalMoves(board);
//        String oppositeColor = Piece.getOppositeColor(color);
        for (Move move : moves) {
            Board currBoard = board.apply(move);
            MoveTreeNode kid = new MoveTreeNode(move, root);
            root.addKid(kid);
            generateLegalMovesTree(kid, currBoard, depth - 1);
        }
        return root;
    }

    /**
     * @param board The current board state
     * @return a list of legal moves
     */
    @Override
    public List<Move> generateLegalMoves(Board board){
        List<Pair<Piece, Integer>> attackers = getAttackersOnKingOfColor(board);
        if(attackers.size() == 0){
            return generateLegalMovesNotInCheck(board);
        }
        else{
            return generateLegalMovesInCheck(board, attackers);
        }
    }
    /**
     * @param board The current board state
     * @return A list of legal moves, disregarding check
     */
    public List<Move> generateLegalMovesNotInCheck(Board board){
        List<Move> moves = new ArrayList<>();
        List<Move> opposingMoves = new ArrayList<>();
        List<Integer> pieceTiles = board.getPieceTiles(board.getNextMoveColor());
        HashMap<Integer, Pin> pieceIdToPinMap = getPieceIdToPinMap(board, board.getNextMoveColor());
        Pair<King, Integer> kingPair = null;
        for(int tile : pieceTiles){
            Piece piece = board.getPieceAt(tile).orElseThrow(() -> new RuntimeException("Piece does not exist on tile " + tile));
            if(!(piece instanceof King)){
                if(piece.getColor().equals(board.getNextMoveColor())) {
                    moves.addAll(generateMovesFor(piece, tile, board, pieceIdToPinMap.get(piece.getId())));  //TODO: pin logic error
                }
                else{
                    opposingMoves.addAll(generateMovesFor(piece, tile, board, null));
                }
            } else if(piece.getColor().equals(board.getNextMoveColor())){
                kingPair = Pair.of((King)piece, tile);
            }
        }
        if(kingPair != null){
            moves.addAll(generateKingMoves(kingPair.left(), kingPair.right(), board, opposingMoves));
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
            return generateKnightMoves((Knight)piece, currentTile, board, pin);
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
        List<Integer> captureDirections = Pawn.captureMoveShifts(currentTile, pawn.getColor());
        int pushDirection = pawn.getDirectionMultiplier() * Board.LENGTH;
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
     * @param knight the piece to be generating moves for
     * @param currentTile the tile of the current piece
     * @param board the current board state
     * @param pin the pin involving the current piece, if exists
     * @return a list of legal knight moves
     */
    public List<Move> generateKnightMoves(Knight knight, int currentTile, Board board, Pin pin){
        if(pin != null){
            return Collections.emptyList();
        }
        return knight.moveShifts(currentTile).stream().
                map(moveShift -> moveShift + currentTile).
                filter(TileUtil::isInBoard).
                filter(tile -> {
                    if(board.getPieceAt(tile).isEmpty()){
                        return true;
                    }
                    if(board.getPieceAt(tile).get().getColor().equals(knight.getColor())){ //piece of same color
                        return false;
                    }
                    return true;
                }).
                map(tile -> new Move(knight, currentTile, tile)).toList();

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
            moves.addAll(getMovesFromTileToEdgeOfBoard(slidingPiece, currentTile, board, pin.direction * -1));
        }
        return moves;
    }

    /**
     * @param king The king to be generating moves for
     * @param currentTile The tile that the king is on
     * @param board The current board state
     * @param allNonKingMoves a list of non King moves by the opposing side
     * @return a list of legal king moves
     */
    public List<Move> generateKingMoves(King king, int currentTile, Board board, List<Move> allNonKingMoves){
        List<Integer> possibleEndTiles = new ArrayList<>(king.moveShifts(currentTile).stream().
                map(moveShift -> moveShift + currentTile).
                filter(TileUtil::isInBoard).
                filter(tile -> board.getPieceAt(tile).
                        map(occupant ->
                                !occupant.getColor().equals(king.getColor())).
                        orElse(true)).toList());

        for(Move move : allNonKingMoves){
            if(move.getPiece().getColor().equals(king.getColor())){
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
        Optional<Integer> optionalOtherKingTile = board.getTileOfKing(Piece.getOppositeColor(king.getColor()));
        if(optionalOtherKingTile.isPresent()){
            int otherKingTile = optionalOtherKingTile.get();
            List<Integer> otherKingEndTiles = king.moveShifts(currentTile).stream().
                    map(moveShifts -> moveShifts + otherKingTile).toList();

            for(int otherKingEndTile : otherKingEndTiles){
                if(possibleEndTiles.contains(otherKingEndTile)){
                    possibleEndTiles.remove(Integer.valueOf(otherKingEndTile));
                }
            }
        }
        return possibleEndTiles.stream()
                .filter(endTile -> !isTileProtectedBy(board, endTile, Piece.getOppositeColor(king.getColor())))
                .map(endTile -> new Move(king, currentTile, endTile))
                .toList();
    }
    public List<Move> generateKingMoves1(King king, int currentTile, Board board){
        String oppositeColor = Piece.getOppositeColor(king.getColor());
        List<Integer> possibleEndTiles = new ArrayList<>(king.moveShifts(currentTile).stream().
                map(moveShift -> moveShift + currentTile).
                filter(TileUtil::isInBoard).
                filter(tile -> board.getPieceAt(tile).
                        map(occupant ->
                                !occupant.getColor().equals(king.getColor())).
                        orElse(true)).
                filter(tile -> !isTileProtectedBy(board, tile, oppositeColor)).toList());
        return possibleEndTiles.stream().map(endTile -> new Move(king, currentTile, endTile)).toList();
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
        List<Integer> directions = SlidingPiece.getAllMoveShifts();
        List<Pin> pins = new ArrayList<>();
        for(int direction : directions){
            Piece prevEncountered = null;

            for(int i = 1; i <= TileUtil.tilesToEdgeOfBoard(kingTile, direction); i++){ //while the tile to check is still on the board
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
                            !slidingPiece.getColor().equals(color)
                    ){
                        pins.add(new Pin(prevEncountered, encountered, direction));
                    }
                    else { // 2 of same color protecting, none will be pinned in this direction
                           // 1 color protecting, and opposite color not sliding, no pings
                        break;
                    }
                }
            }
        }
        return pins;
    }

    /**
     *
     * @param pawn The pawn being checked
     * @param board The current board state
     * @param pin The pin involving the current pawn, if exists
     * @param direction The direction of the capture
     * @return true of the capture is valid
     */
    public boolean isDiagonalCaptureValid(Pawn pawn, int currentTile, Board board, Pin pin, int direction){
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
            if(!TileUtil.isOnRankForEnPassant(currentTile, pawn.getColor())){ //not on right rank for en passant
                return false;
            }
            Move previousMove = optionalMove.get();
            if(!(previousMove.getPiece() instanceof Pawn)){ //previous move was not a pawn move
                return false;
            }
            //if the previous move involves a pawn ending up on the tile behind the resultant tile
            return previousMove.getEndTile() == resultantTile + (-8 * pawn.getDirectionMultiplier());
        }
        Piece occupant = endTile.get();
        return !occupant.getColor().equals(pawn.getColor());
    }

    /**
     * @param board the current board state
     * @return a list of attackers on the king
     */
    public List<Pair<Piece, Integer>> getAttackersOnKingOfColor(Board board){
        Optional<Integer> optionalKingTile = board.getTileOfKing(board.getNextMoveColor());
        if(optionalKingTile.isEmpty()){
            return Collections.emptyList();
        }
        int kingTile = optionalKingTile.get();
        return getPiecesProtectingSquare(board, kingTile, Piece.getOppositeColor(board.getNextMoveColor()), false, true);
    }

    /**
     *
     * @param board The current board state
     * @param tile The tile to be checked
     * @param color The color of the pieces to look for
     * @return a list of tuples containing the pieces protecting given tile and their tile numbers
     */
    public List<Pair<Piece, Integer>> getPiecesProtectingSquare(
            Board board, int tile, String color, boolean toBlockAttacker, boolean includePinnedPieces
    ){
        List<Pair<Piece, Integer>> defenders = new ArrayList<>();

        //checking for knights
        List<Integer> moveShifts = Knight.getMoveShifts(tile);
        for(int moveShift : moveShifts){
            int resultantTile = moveShift + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Knight && occupant.getColor().equals(color)){
                defenders.add(Pair.of(occupant, resultantTile));
            }
        }

        //checking for sliding pieces
        moveShifts = SlidingPiece.getAllMoveShifts();
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
                    defenders.add(Pair.of(slidingPiece, resultantTile));
                }
                break;
            }
        }

        //check for pawns
        List<Integer> moveDirections = (toBlockAttacker) ?
                Arrays.asList(-8, -16, 8, 16) :
                Pawn.captureMoveShifts(tile, Piece.getOppositeColor(color));
        for(int moveDirection : moveDirections){
            int resultantTile = moveDirection + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Pawn
                    && occupant.getColor().equals(color)
                    && ((Pawn)occupant).getDirectionMultiplier() * (tile - resultantTile) > 0
            ){
                defenders.add(Pair.of(occupant, resultantTile));
            }
        }

        if(includePinnedPieces){
            HashMap<Integer, Pin> pieceIdToPinMap = getPieceIdToPinMap(board, color);
            return defenders.stream().
                    filter(defenderInfo -> !pieceIdToPinMap.containsKey(defenderInfo.left().getId())).toList();
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
        List<Integer> moveShifts = Knight.getMoveShifts(tile);

        for(int moveShift : moveShifts){
            int resultantTile = moveShift + tile;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Knight && occupant.getColor().equals(color)){
                return true;
            }
        }
        //checking for sliding pieces
        //TODO:  revisit logic, as not woking
        moveShifts = SlidingPiece.getAllMoveShifts();
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
                if(occupant.getColor().equals(color)
                        && occupant instanceof SlidingPiece slidingPiece
                        && slidingPiece.getMoveShifts().contains(-1 * direction)
                ){
                    return true;
                }
                break;
            }
        }

        //check for pawns
        List<Integer> captureDirections = Pawn.captureMoveShifts(tile, color);
        for(int captureDirection : captureDirections){
            int resultantTile = tile - captureDirection;
            Optional<Piece> optionalOccupant = board.getPieceAt(resultantTile);
            if(optionalOccupant.isEmpty()){
                continue;
            }
            Piece occupant = optionalOccupant.get();
            if(occupant instanceof Pawn && occupant.getColor().equals(color)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param board the current board state
     * @param attackers list of pieces threatening the king and their tile numbers
     * @return a list of legal moves for defending the king from the check
     */
    public List<Move> generateLegalMovesInCheck(Board board, List<Pair<Piece, Integer>> attackers){
        List<Move> moves = new ArrayList<>();
        String oppositeColor = Piece.getOppositeColor(board.getNextMoveColor());
        Optional<Integer> optionalKingTile = board.getTileOfKing(board.getNextMoveColor());
        if(optionalKingTile.isEmpty()){
            throw new IllegalStateException(board.getNextMoveColor() + " does not have a king");
        }
        int kingTile = optionalKingTile.get();
        King king = (King)board.getIndexPieceMap().get(kingTile);
        //generate legal king moves first
        List<Integer> moveShifts = king.moveShifts(kingTile);
        for(int moveShift : moveShifts){
            int resultantTile = moveShift + kingTile;
            if(board.getPieceAt(resultantTile).isPresent() && board.getPieceAt(resultantTile).get().getColor().equals(board.getNextMoveColor())){
                continue;
            }
            if(isTileProtectedBy(board, resultantTile, oppositeColor)
            ){
                continue;
            }
            moves.add(new Move(king, kingTile, resultantTile));
        }
        if(attackers.size() == 2 ){
            return moves;
        }
        Pair<Piece, Integer> attackerInfo = attackers.get(0);
        Piece attacker = attackerInfo.left();
        if(attacker instanceof Knight || attacker instanceof Pawn){
            int attackerTile = attackerInfo.right();

           List<Pair<Piece, Integer>> defendersInfo = getPiecesProtectingSquare(board, attackerTile, board.getNextMoveColor(), true,false);
           for(Pair<Piece, Integer> defenderInfo : defendersInfo){
               moves.add(new Move(defenderInfo.left(), defenderInfo.right(), attackerTile));
           }

           return moves;
        }
        else if(attacker instanceof SlidingPiece){
            int attackerTile = attackerInfo.right();
            int attackerDirection = TileUtil.getSlidingDirection(attackerTile, kingTile);
            int distance = Math.abs((attackerTile - kingTile)/attackerDirection );
            for(int i = 1; i < distance; i++){
                int currentTile = attackerTile + i * attackerDirection;
                List<Pair<Piece, Integer>> defendersInfo = getPiecesProtectingSquare(board, currentTile, board.getNextMoveColor(), true, false);
                for(Pair<Piece, Integer> defenderInfo : defendersInfo){
                    moves.add(new Move(defenderInfo.left(), defenderInfo.right(), currentTile));
                }
            }
            return moves;
        }
        System.out.println("Not sure how you got here, but Congratulations");
        return moves;
    }
}
