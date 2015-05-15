import com.aram.gol.Board

class BootStrap {

    def init = { servletContext ->

        new Board(rows: 20, columns: 20, boardData: [-123, 2, 96, 48, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                                     0, 0, 0, 0, 0, 0, 0, 0]).save flush: true

    }
    def destroy = {
    }
}
