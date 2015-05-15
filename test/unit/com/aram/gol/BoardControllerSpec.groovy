package com.aram.gol


import grails.test.mixin.*
import org.springframework.http.HttpStatus
import spock.lang.*

@TestFor(BoardController)
@Mock(Board)
class BoardControllerSpec extends Specification {

    static doWithSpring = {
        boardService(BoardService)
        gameService(GameService)
    }

    def populateValidParams(params) {
        assert params != null
        params["rows"] = '5'
        params["columns"] = '5'
        params["aliveFields"] = '0,4,8'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.boardInstanceList
        model.boardInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.boardInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def board = new Board()
        board.validate()
        controller.save(board)

        then: "The create view is rendered again with the correct model"
        model.boardInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()

        populateValidParams(params)
        board = new Board(params)
        controller.save(board)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/board/show/1'
        controller.flash.message != null
        Board.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        params['id'] = '1'
        def board = new Board(params)
        controller.show(board)

        then: "A model is populated containing the domain instance"
        model.boardInstance == board
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == HttpStatus.NOT_FOUND.value()

        when: "A domain instance is passed to the edit action"
        params['id'] = '1'
        def board = new Board(params)
        controller.edit(board)

        then: "A model is populated containing the domain instance"
        model.boardInstance == board
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        controller.update(null)

        then: "A 404 error is returned"
        response.redirectedUrl == '/'
        flash.message != null

        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def board = new Board()
        board.validate()
        controller.update(board)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.boardInstance == board

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        board = new Board(params)
        controller.save(board)
        response.reset()
        board.rows = 10
        controller.update(board)

        then: "A redirect is issues to the show action"

        response.redirectedUrl == "/board/show/$board.id"
        flash.message != null
        Board.read(board.id).rows == 10
    }

    void "Test that the delete action deletes an instance if it exists"() {

        when: "A domain instance is created"
        request.method = 'POST'
        populateValidParams(params)
        def board = new Board(params)
        controller.save(board)

        then: "It exists"
        Board.count() == 1

        when: "The domain instance is passed to the delete action"
        response.reset()
        request.method = 'DELETE'
        controller.delete(board)

        then: "The instance is deleted"
        Board.count() == 0
        response.status == HttpStatus.NO_CONTENT.value()

    }
}
