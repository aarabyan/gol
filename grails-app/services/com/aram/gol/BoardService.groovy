package com.aram.gol

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class BoardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardService.class)

    static transactional = false

    def Board initBoard(Map params) {
        Board board = BoardFactory.newBoard(params.rows, params.columns)
        setAliveFields(board, params)
        return board
    }

    def void initFields(Board board) {
        int globalPossition, row, col, offset
        board.boardData.eachWithIndex { byte aByte, idx ->
            //if aByte value is 0 all bits are 0, i.e all matrix are dead, no need to porcess ...
            if (aByte) {
                offset = idx * 8
                (0..7).each { it ->
                    if (getBit(aByte, it)) {
                        globalPossition = offset + it
                        row = globalPossition / board.columns
                        col = globalPossition % board.columns
                        board.setAlive(row, col)
                    }
                }
            }
        }
    }

    private setAliveFields(Board board, Map params) {

        def aliveFields = getAliveFields(params)
        LOGGER.debug("Number of alive fields  = {}", aliveFields?.size())

        aliveFields.each { int it ->
            int destRow = it / board.columns
            int destCol = it % board.columns
            LOGGER.trace("id {} parsed as row {} col{}", it, destRow, destCol)
            board.setAlive(destRow, destCol)
        }
    }

    def setBoardData(Board board, Map params) {
        board.boardData = new byte[getBytesSize(board)]

        def aliveFields = getAliveFields(params)
        //set corresponding bit to 1 for alive field.
        aliveFields.each {
            int byteIndex = it / 8
            int positionInByte = it % 8
            board.boardData[byteIndex] = setBit(board.boardData[byteIndex], positionInByte)
        }
    }

    private Collection<Integer> getAliveFields(Map params) {
        if (params.aliveFields instanceof String) {
            return params.aliveFields.split(",").findAll {
                it
            }.collect { Integer.parseInt(it) }
        } else {
            return params.aliveFields?.collect { Integer.parseInt(it) }
        }
    }

    private int getBytesSize(Board board) {
        int size = board.rows * board.columns / 8 + 1
        LOGGER.trace("Data size for {}x{} board is {}byte", board.rows, board.columns, size)
        return size
    }

    private boolean getBit(byte num, int pos) {
        return ((num & (1 << pos)) != 0);
    }

    private byte setBit(byte num, int pos) {
        return num | (1 << pos);
    }
}
