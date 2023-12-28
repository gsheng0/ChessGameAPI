package com.example.chessenginegame.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Resources {
    public static final String ABSOLUTE_PATH_PREFIX = "/Users/gsheng/IdeaProjects/ChessEngineGame/src/main/resources/";

    public static Image getImageFromPath(String path){
        File file = new File(path);
        try{
            return ImageIO.read(file);
        } catch(Exception e){
            throw new RuntimeException(String.format("Error retrieving image with path '%s'", path));
        }
    }
    public static HashMap<Character, Image> getCharacterToImageHashMap(){
        HashMap<Character, Image> characterImageHashMap = new HashMap<>();
        Image BLACK_KING = getImageFromPath(String.format("%s/black_king.png", ABSOLUTE_PATH_PREFIX));
        Image WHITE_KING = getImageFromPath(String.format("%s/white_king.png", ABSOLUTE_PATH_PREFIX));
        Image BLACK_QUEEN = getImageFromPath(String.format("%s/black_queen.png", ABSOLUTE_PATH_PREFIX));
        Image WHITE_QUEEN = getImageFromPath(String.format("%s/white_queen.png", ABSOLUTE_PATH_PREFIX));
        Image BLACK_ROOK = getImageFromPath(String.format("%s/black_rook.png", ABSOLUTE_PATH_PREFIX));
        Image WHITE_ROOK = getImageFromPath(String.format("%s/white_rook.png", ABSOLUTE_PATH_PREFIX));
        Image BLACK_BISHOP = getImageFromPath(String.format("%s/black_bishop.png", ABSOLUTE_PATH_PREFIX));
        Image WHITE_BISHOP = getImageFromPath(String.format("%s/white_bishop.png", ABSOLUTE_PATH_PREFIX));
        Image BLACK_KNIGHT = getImageFromPath(String.format("%s/black_knight.png", ABSOLUTE_PATH_PREFIX));
        Image WHITE_KNIGHT = getImageFromPath(String.format("%s/white_knight.png", ABSOLUTE_PATH_PREFIX));
        Image BLACK_PAWN = getImageFromPath(String.format("%s/black_pawn.png", ABSOLUTE_PATH_PREFIX));
        Image WHITE_PAWN = getImageFromPath(String.format("%s/white_pawn.png", ABSOLUTE_PATH_PREFIX));

        characterImageHashMap.put('k', BLACK_KING);
        characterImageHashMap.put('q', BLACK_QUEEN);
        characterImageHashMap.put('r', BLACK_ROOK);
        characterImageHashMap.put('b', BLACK_BISHOP);
        characterImageHashMap.put('n', BLACK_KNIGHT);
        characterImageHashMap.put('p', BLACK_PAWN);

        characterImageHashMap.put('K', WHITE_KING);
        characterImageHashMap.put('Q', WHITE_QUEEN);
        characterImageHashMap.put('R', WHITE_ROOK);
        characterImageHashMap.put('B', WHITE_BISHOP);
        characterImageHashMap.put('N', WHITE_KNIGHT);
        characterImageHashMap.put('P', WHITE_PAWN);

        return characterImageHashMap;
    }
}
