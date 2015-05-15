package com.aram.gol

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Board)
class BoardSpec extends Specification {

    void "test constraints"() {

        when: 'row or column is less than 3'
        def board = BoardFactory.newBoard(1, 2)
        then: 'validation should fail'
        !board.validate()

        board.errors.getFieldError("rows").code == 'min.notmet'
        board.errors.getFieldError("columns").code == 'min.notmet'
        board.errors.getFieldError("boardData").code == 'nullable'


        when: 'when boardData is not initialized'
        board = BoardFactory.newBoard(5, 5)
        !board.validate()

        then: 'boardData should have error'
        board.errors.getFieldError("boardData").code == 'nullable'

        when: 'boardData is initialized '
        board.boardData = new byte[10]
        then: 'validation should pass'
        board.validate()
        !board.hasErrors()
    }
}