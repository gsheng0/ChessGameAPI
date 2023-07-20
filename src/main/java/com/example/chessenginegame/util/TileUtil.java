package com.example.chessenginegame.util;
public class TileUtil {
    public static boolean isInBoard(int tile){
        return tile >= 0 && tile < 64;
    }
    public static int getFile(int tile){
        return tile % 8;
    }
}
