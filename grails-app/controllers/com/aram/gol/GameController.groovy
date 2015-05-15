package com.aram.gol

import org.springframework.transaction.annotation.Transactional
import groovy.json.JsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Propagation
import static com.aram.gol.BoardUtils.*

@Transactional(propagation = Propagation.NEVER)
class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class)

    static allowedMethods = [nextStep: "POST"]

    def boardService
    def gameService

    def nextStep() {
        LOGGER.debug("Next Step params = {}", params)

        Board current = boardService.initBoard(params)
        Board next = gameService.nextStep(current)

        Map jsonMap = [:]
        jsonMap.put('alive', next.alive)
        jsonMap.put('changed', isChanged(current, next))
        jsonMap.put('generation', nextGeneration(params))

        render new JsonBuilder(jsonMap)

    }

}
