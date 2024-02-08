package com.example.chessenginegame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
  @Test
  public void test_printBoard() {
    Board.printBoard();
  }

  @Test
  public void test_printBoardMatrix() {
    Board board = Board.startingPosition();
    board.printBoardMatrix();
  }
}