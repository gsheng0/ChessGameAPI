package com.example.chessenginegame.service;

import com.example.chessenginegame.model.Board;
import com.example.chessenginegame.model.Move;
import com.example.chessenginegame.model.MoveTreeNode;

import java.util.List;

public interface MoveGeneratorService {
    List<Move> generateLegalMoves(Board board);
    MoveTreeNode generateLegalMovesTree(MoveTreeNode root, Board board, String color, int depth);
}
