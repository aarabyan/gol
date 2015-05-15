package com.aram.gol

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(BoardService)
class BoardServiceSpec extends Specification {

    void "test  initBoard"() {
        Map params = [:]
        params.rows = 3
        params.columns = 3
        params.aliveFields = "0,4,8"

        Board board = service.initBoard(params)

        expect:
        board.rows == 3
        board.columns == 3
        board.isAlive(0, 0)
        !board.isAlive(0, 1)
        !board.isAlive(0, 2)
        !board.isAlive(1, 0)
        board.isAlive(1, 1)
        !board.isAlive(1, 2)
        !board.isAlive(2, 0)
        !board.isAlive(2, 1)
        board.isAlive(2, 2)

    }

    void "test  initFields"() {
        Board board = BoardFactory.newBoard(3, 3);
        board.boardData = [17, 1] as byte[]
        service.initFields(board)
        expect:
        board.isAlive(0, 0)
        !board.isAlive(0, 1)
        !board.isAlive(0, 2)
        !board.isAlive(1, 0)
        board.isAlive(1, 1)
        !board.isAlive(1, 2)
        !board.isAlive(2, 0)
        !board.isAlive(2, 1)
        board.isAlive(2, 2)
    }

    void "test  setAliveFields"() {
        Board board = BoardFactory.newBoard(3, 3);
        Map params = [aliveFields: "0,4,8"]
        service.setAliveFields(board, params)

        expect:
        board.isAlive(0, 0)
        !board.isAlive(0, 1)
        !board.isAlive(0, 2)
        !board.isAlive(1, 0)
        board.isAlive(1, 1)
        !board.isAlive(1, 2)
        !board.isAlive(2, 0)
        !board.isAlive(2, 1)
        board.isAlive(2, 2)
    }

    void "test  setBoardData"() {
        Board board = BoardFactory.newBoard(3, 3);
        Map params = [aliveFields: "0,4,8"]
        service.setBoardData(board, params)

        expect:
        board.boardData == [17, 1] as byte[]

    }


    void "test getAliveFields"() {
        Collection<Integer> collection1 = service.getAliveFields([aliveFields: "1,2,3"])
        Collection<Integer> collection2 = service.getAliveFields([aliveFields: ["5", "6"]])

        expect:
        service.getAliveFields([:]) == null

        collection1.contains(1)
        collection1.contains(2)
        collection1.contains(3)
        collection1.size() == 3

        collection2.contains(5)
        collection2.contains(6)
        collection2.size() == 2


    }

    private Collection<Integer> getAliveFields(Map params) {
        if (params?.aliveFields instanceof String) {
            return params?.aliveFields?.split(",")?.collect { Integer.parseInt(it) }
        } else {
            return params?.aliveFields?.collect { Integer.parseInt(it) }
        }
    }

    void "test getBytesSize"() {
        Board board1 = BoardFactory.newBoard(8, 8)
        Board board2 = BoardFactory.newBoard(20, 20)

        expect:
        service.getBytesSize(board1) == 9
        service.getBytesSize(board2) == 51
    }


    void "test getBit"() {
        expect:
        !service.getBit((byte) 0, 0)
        service.getBit((byte) 1, 0)

        service.getBit((byte) 3, 0)
        service.getBit((byte) 3, 1)
        !service.getBit((byte) 3, 2)

    }

    void "test setBit"() {
        expect:
        service.setBit((byte) 0, 0) == (byte) 1
        service.setBit((byte) 0, 1) == (byte) 2
        service.setBit((byte) 0, 2) == (byte) 4
        service.setBit((byte) 0, 3) == (byte) 8
        service.setBit((byte) 0, 4) == (byte) 16
        service.setBit((byte) 0, 5) == (byte) 32
        service.setBit((byte) 0, 6) == (byte) 64
        service.setBit((byte) 0, 7) == (byte) 128
    }
}
