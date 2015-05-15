package com.aram.gol

import javax.annotation.PreDestroy
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class GameService {
    static transactional = false

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public Board nextStep(Board initialBoard) {
        Board resultBoard = BoardFactory.newBoard(initialBoard)

        for (int rowIdx = 0; rowIdx < initialBoard.rows; rowIdx++) {
            for (int colIdx = 0; colIdx < initialBoard.columns; colIdx++) {

                //todo optimze ...
                Future results = threadPool.submit(new Callable() {
                    Boolean call() throws Exception {
                        return cellNextValue(initialBoard, rowIdx, colIdx)
                    }
                })

                if (results.get()) {
                    resultBoard.setAlive(rowIdx, colIdx)
                } else {
                    resultBoard.setDead(rowIdx, colIdx)
                }

            }
        }
        return resultBoard
    }

    private Boolean cellNextValue(Board board, int row, int col) {
        byte neighbours = countNeighbours(board, row, col);
        if (board.isAlive(row, col)) {
            return cellNextValueForAlive(neighbours);
        } else {
            return cellNextValueForDead(neighbours);
        }
    }

    private byte countNeighbours(Board board, int row, int col) {
        byte count = 0;

        for (Neighbour direction : Neighbour.values()) {
            if (board.isAlive(row + direction.dRow, col + direction.dCol)) {
                ++count;
            }
        }
        return count;
    }

    private Boolean cellNextValueForAlive(byte neighbours) {
        return neighbours == 2 || neighbours == 3;
    }


    private Boolean cellNextValueForDead(byte neighbours) {
        return neighbours == 3;
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        threadPool.shutdown()
    }
}
