package com.example.chessenginegame.util;

import java.util.HashMap;

public class TileUtil {
    private static HashMap<Integer, HashMap<Integer, Integer>> tilesToEdgeMapMap;

    public static boolean isInBoard(int tile){
        return tile >= 0 && tile < 64;
    }
    public static int getFile(int tile){
        return tile % 8;
    }
    public static int getRank(int tile){
        return tile / 8;
    }

    /**
     * @param tile the current tile
     * @param direction the direction
     * @return the number of tiles between the current tile and the edge of the board in the given direction
     */
    public static int tilesToEdgeOfBoard(int tile, int direction){
        return tilesToEdgeMapMap.get(direction).get(tile);
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
