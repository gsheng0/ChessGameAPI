package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;

import java.util.List;

public interface MoveGenerator {
    List<Move> generateLegalMoves(Board board, String color);
}
