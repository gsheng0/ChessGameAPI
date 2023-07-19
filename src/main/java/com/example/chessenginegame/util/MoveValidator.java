package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;

public interface MoveValidator {
    boolean isValid(Move move, Board board);
}
