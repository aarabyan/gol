package com.aram.gol


class BoardFactory {
    private BoardFactory() {
    }

    public static Board newBoard(Board board) {
        newBoard(board.rows, board.columns)
    }

    public static Board newBoard(def rows, def columns) {
        Board result = new Board(rows: rows, columns: columns)
        result.init()
        return result
    }
}
