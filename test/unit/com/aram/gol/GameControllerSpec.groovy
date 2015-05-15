package com.aram.gol

import grails.test.mixin.TestFor
import spock.lang.Specification
import static javax.servlet.http.HttpServletResponse.*
/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(GameController)
class GameControllerSpec extends Specification {

    static doWithSpring = {
        boardService(BoardService)
        gameService(GameService)
    }


    void 'test nextStep'() {
        when:
        params.aliveFields = '0,1,6,7'
        params.generation = '0'
        params.rows = '5'
        params.columns = '5'


        request.method = 'POST'
        request.makeAjaxRequest()


        controller.nextStep()

        then:
        response.text == "{\"alive\":[0,1,2,5,6,7],\"changed\":true,\"generation\":1}"
        response.json.alive == [0, 1, 2, 5, 6, 7]
        response.json.changed == true
        response.json.generation == 1
    }

    void 'test nextStep noChange'() {
        when:
        params.aliveFields = '6,7,11,12'
        params.generation = '1'
        params.rows = '5'
        params.columns = '5'


        request.method = 'POST'
        request.makeAjaxRequest()


        controller.nextStep()

        then:
        response.status == SC_OK
        response.text == "{\"alive\":[6,7,11,12],\"changed\":false,\"generation\":2}"
        response.json.alive == [6, 7, 11, 12]
        response.json.changed == false
        response.json.generation == 2
    }

    void "test an invalid request method"() {
            when:
            request.method = 'DELETE'
            controller.nextStep()

            then:
            response.status == SC_METHOD_NOT_ALLOWED
        }
}
