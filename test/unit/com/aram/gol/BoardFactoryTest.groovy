package com.aram.gol

import org.junit.Test

import static com.aram.gol.BoardFactory.*

class BoardFactoryTest {

    @Test
    public void testNewBoardRowCol() {
        Board board = newBoard(3, 5)

        assert board.rows == 3
        assert board.columns == 5

        for (int row = 0; row < board.rows; row++) {
            for (int col = 0; col < board.columns; col++) {
                assert !board.isAlive(row, col)
            }
        }
    }

    @Test
    public void testNewBoardFromBoard() {
        Board board = newBoard(3, 5)
        Board result = newBoard(board)

        assert board.rows == result.rows
        assert board.columns == result.columns

    }
}