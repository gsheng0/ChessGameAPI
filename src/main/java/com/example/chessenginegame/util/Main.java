package com.example.chessenginegame.util;

import com.example.chessenginegame.model.Board;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Window window = new Window();
        ChessGameTester tester = new ChessGameTester();
        List<Board> boards = tester.generateMoves(Board.startingPosition(), 0, 2);
        window.setBoards(boards);
        window.show();
    }
}
