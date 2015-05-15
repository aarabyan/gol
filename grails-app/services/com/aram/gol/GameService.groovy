package com.aram.gol

import static grails.async.Promises.task
import static grails.async.Promises.waitAll
import javax.annotation.PreDestroy
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GameService {
    static transactional = false

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public Board nextStep(Board initialBoard) {
        Board resultBoard = BoardFactory.newBoard(initialBoard)

        def tasks = []
        for (final int row = 0; row < initialBoard.rows; row++) {
            final rowIdx = row
            tasks << task {
                for (int col = 0; col < initialBoard.columns; col++) {
                    if (cellNextValue(initialBoard, rowIdx, col)) {
                        resultBoard.setAlive(rowIdx, col)
                    } else {
                        resultBoard.setDead(rowIdx, col)
                    }
                }
            }
        }

        waitAll(tasks)
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
