package com.example.chessenginegame.util;

import com.example.chessenginegame.components.Board;
import com.example.chessenginegame.components.Move;

public interface MoveValidator {
    boolean isValid(Move move, Board board);
}
