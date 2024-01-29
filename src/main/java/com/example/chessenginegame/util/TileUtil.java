package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Constants;
import com.example.chessenginegame.model.piece.SlidingPiece;

import java.util.HashMap;
import java.util.List;

public class TileUtil {
    private static HashMap<Integer, HashMap<Integer, Integer>> tilesToEdgeMapMap;

    /**
     *
     * @param tile the integer representation of the tile
     * @return true if the tile exists within the board
     */
    public static boolean isInBoard(int tile){
        return tile >= Board.MIN_TILE && tile <= Board.MAX_TILE;
    }

    /**
     * files are numbered from left to right, in white's perspective
     * @param tile the integer representation of the tile
     * @return the file that the tile is in
     */
    public static int getFile(int tile){
        return tile % Board.LENGTH;
    }

    /**
     * ranks are numbered from top to bottom, in white perspective
     * @param tile the integer representation of the tile
     * @return the rank that the tile is in
     */
    public static int getRank(int tile){
        return tile / Board.LENGTH;
    }

    /**
     * @param tile the current tile
     * @param direction the direction
     * @return the number of tiles between the current tile and the edge of the board in the given direction
     */
    public static int tilesToEdgeOfBoard(int tile, int direction){
        return tilesToEdgeMapMap.get(direction).get(tile);
    }

    /**
     *
     * @param from the tile to start the direction vector from
     * @param to the tile to end the direction vector towards
     * @return
     */
    public static int getSlidingDirection(int from, int to){
        //TODO: Remove negative directions from usage in this method
        List<Integer> moveShifts = SlidingPiece.getAllMoveShifts();
        int difference = to - from;
        for(int moveShift : moveShifts){
            int multiplier = Math.abs(difference)/difference;
            if(Math.abs(difference) % moveShift == 0 && Math.abs(difference) / moveShift < 8){
                return multiplier * moveShift;
            }
        }
        return -1;
    }

    /**
     *
     * @param tile the tile of the pawn
     * @param color the color of the pawn
     * @return true if the pawn is on approriate rank for en passant to be a legal move
     */
    public static boolean isOnRankForEnPassant(int tile, String color){
        int rank = getRank(tile);
        if(color.equals(Constants.WHITE)){
            return rank == 3;
        }
        else if(color.equals(Constants.BLACK)){
            return rank == 4;
        }
        throw new IllegalArgumentException(color + " is not a valid color");
    }

    /**
     *
     * @param tile The tile of the pawn
     * @param color The color of the pawn
     * @return true if the pawn is on its starting tile
     */
    public static boolean isOnStartingRank(int tile, String color){
        int rank = getRank(tile);
        if(color.equals(Constants.WHITE)){
            return rank == 6;
        }
        else if(color.equals(Constants.BLACK)){
            return rank == 1;
        }
        throw new IllegalArgumentException(color + " is not a valid color");
    }

    /**
     *
     * @param coordinates the string representation of a chess tile ie: "g3" or "F7"
     *                    Only the first two characters are checked
     * @return the corresponding index in the 1d representation of the board. Passing in "A8" returns 0
     * @throws IllegalArgumentException if an invalid coordinate is entered.
     */
    public static int getIndexFromNamedTile(String coordinates){
        coordinates = coordinates.toLowerCase();
        int letter = coordinates.charAt(0) - 'a';
        int number = Integer.parseInt(coordinates.substring(1));
        if(number < 1 || number > 8 || letter < 0 || letter > 7){
            throw new IllegalArgumentException("Invalid input: " + coordinates);
        }
        return letter + 8 * (8 - number);
    }

    /**
     *
     * @param tileIndex the tile to be named
     * @return The corresponding standard chess name for the tile
     */
    public static String getNamedTileFromIndex(int tileIndex){
        int file = getFile(tileIndex);
        int rank = getRank(tileIndex);
        char letter = (char)('a' + file);
        int number = rank + 1;
        return "" + letter + number;
    }

    static{
        tilesToEdgeMapMap = new HashMap<>();
        HashMap<Integer, Integer> minusOne = new HashMap<>();
        HashMap<Integer, Integer> plusOne = new HashMap<>();
        HashMap<Integer, Integer> minusEight = new HashMap<>();
        HashMap<Integer, Integer> plusEight = new HashMap<>();
        HashMap<Integer, Integer> minusSeven = new HashMap<>();
        HashMap<Integer, Integer> plusSeven = new HashMap<>();
        HashMap<Integer, Integer> minusNine = new HashMap<>();
        HashMap<Integer, Integer> plusNine = new HashMap<>();
        for(int i = 0; i < 64; i++){
            int file = getFile(i);
            int rank = getRank(i);
            minusOne.put(i, file);
            plusOne.put(i, (7 - file) % 8);
            minusEight.put(i, rank);
            plusEight.put(i, (7 - rank) % 8);
            minusSeven.put(i, Math.min(7 - file, rank));
            plusSeven.put(i, Math.min(file, 7 - rank));
            minusNine.put(i, Math.min(file, rank));
            plusNine.put(i, Math.min(7 - file, 7 - rank));
        }

        tilesToEdgeMapMap.put(-1, minusOne);
        tilesToEdgeMapMap.put(1, plusOne);
        tilesToEdgeMapMap.put(-8, minusEight);
        tilesToEdgeMapMap.put(8, plusEight);
        tilesToEdgeMapMap.put(-7, minusSeven);
        tilesToEdgeMapMap.put(7, plusSeven);
        tilesToEdgeMapMap.put(-9, minusNine);
        tilesToEdgeMapMap.put(9, plusNine);
    }
}
