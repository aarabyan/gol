package com.aram.gol

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(GameService)
class GameServiceSpec extends Specification {


    void "test nextStep"() {
        Board initialBoard = BoardFactory.newBoard(5, 5)
        initialBoard.setAlive(0, 0)
        initialBoard.setAlive(0, 1)
        initialBoard.setAlive(1, 0)
        initialBoard.setAlive(1, 1)

        Board resultBoard = service.nextStep(initialBoard)

        expect:
        assert resultBoard.isAlive(0, 0)
        assert resultBoard.isAlive(0, 1)
        assert resultBoard.isAlive(1, 0)
        assert resultBoard.isAlive(1, 1)

        for (int row = 2; row < initialBoard.rows; row++) {
            for (int col = 2; col < initialBoard.columns; col++) {
                assert !resultBoard.isAlive(row, col)
            }
        }
    }


    void "test cellNextValue"() {
        Board board = BoardFactory.newBoard(5, 5)
        board.setAlive(1, 0)
        board.setAlive(1, 1)
        board.setAlive(1, 2)

        expect:
        !service.cellNextValue(board, 0, 0)
        !service.cellNextValue(board, 1, 0)
        !service.cellNextValue(board, 2, 0)

        service.cellNextValue(board, 0, 1)
        service.cellNextValue(board, 1, 1)
        service.cellNextValue(board, 2, 1)

        !service.cellNextValue(board, 0, 2)
        !service.cellNextValue(board, 1, 2)
        !service.cellNextValue(board, 2, 2)

    }

    void "test countNeighbours"() {
        Board board = BoardFactory.newBoard(5, 5)
        board.setAlive(2, 2)

        expect:
        service.countNeighbours(board, 2, 2) == 0
        service.countNeighbours(board, 0, 0) == 0
        service.countNeighbours(board, 4, 4) == 0

        service.countNeighbours(board, 1, 1) == 1
        service.countNeighbours(board, 1, 2) == 1
        service.countNeighbours(board, 1, 3) == 1
        service.countNeighbours(board, 2, 1) == 1
        service.countNeighbours(board, 2, 3) == 1
        service.countNeighbours(board, 3, 1) == 1
        service.countNeighbours(board, 3, 2) == 1
        service.countNeighbours(board, 3, 3) == 1
    }

    void "test cellNextValueForAlive"() {
        expect:
        !service.cellNextValueForAlive((byte) 1)
        service.cellNextValueForAlive((byte) 2)
        service.cellNextValueForAlive((byte) 3)
        !service.cellNextValueForAlive((byte) 4)
    }

    void "test cellNextValueForDead"() {
        expect:
        service.cellNextValueForDead((byte) 3)
        !service.cellNextValueForDead((byte) 4)

    }
}