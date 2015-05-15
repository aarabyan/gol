package com.aram.gol

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BoardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class)

    private static final int DEFAULT_SIZE = 20

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    def boardService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Board.list(params), model: [boardInstanceCount: Board.count()]
    }

    def show(Board boardInstance) {
        if (boardInstance == null) {
            notFound()
            return
        }
        boardInstance.init()
        boardService.initFields(boardInstance)
        respond boardInstance
    }

    def create() {
        Board boardInstance = BoardFactory.newBoard(
                params.rows ?: DEFAULT_SIZE,
                params.columns ?: DEFAULT_SIZE
        )
        respond boardInstance
    }

    @Transactional
    def save(Board boardInstance) {
        LOGGER.debug("Save board with params {}", params)

        if (boardInstance == null) {
            notFound()
            return
        }

        boardService.setBoardData(boardInstance, params)
        boardInstance.validate()

        if (boardInstance.hasErrors()) {
            respond boardInstance.errors, view: 'create'
            return
        }

        boardInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'board.label', default: 'Board'), boardInstance.id])
                redirect boardInstance
            }
            '*' { respond boardInstance, [status: CREATED] }
        }
    }

    def edit(Board boardInstance) {
        if (boardInstance == null) {
            notFound()
            return
        }
        boardInstance.init()
        boardService.initFields(boardInstance)
        respond boardInstance
    }

    @Transactional
    def update(Board boardInstance) {
        if (boardInstance == null) {
            notFound()
            return
        }
        boardService.setBoardData(boardInstance, params)
        boardInstance.validate()

        if (boardInstance.hasErrors()) {
            respond boardInstance.errors, view: 'edit'
            return
        }

        boardInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Board.label', default: 'Board'), boardInstance.id])
                redirect boardInstance
            }
            '*' { respond boardInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Board boardInstance) {

        if (boardInstance == null) {
            notFound()
            return
        }

        boardInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Board.label', default: 'Board'), boardInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'board.label', default: 'Board'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
