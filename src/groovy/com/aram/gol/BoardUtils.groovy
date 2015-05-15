package com.aram.gol


class BoardUtils {

    private BoardUtils() {
    }

    public static boolean isChanged(Board current, Board next) {
        for (int row = 0; row < current.rows; row++) {
            for (int col = 0; col < current.columns; col++) {
                if (current.isAlive(row, col) != next.isAlive(row, col)) {
                    return true
                }
            }
        }
        return false
    }

    public static int nextGeneration(Map params) {
        Integer.parseInt(params.generation ?: "0") + 1
    }
}
