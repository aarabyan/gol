package com.aram.gol

class Board {

    int rows
    int columns
    byte[] boardData

    private boolean[][] fields

    static transients = ['fields']

    static constraints = {
        rows nullable: false, min: 3
        columns nullable: false, min: 3
        boardData nullable: false

    }

    static mapping = {
        version false
        boardData sqlType: 'BLOB'
    }

    public void init() {
        if (!fields) {
            fields = new boolean[rows][columns]
        }
    }

    public void setAlive(int row, int column) {
        fields[row][column] = true
    }

    public void setDead(int row, int column) {
        fields[row][column] = false
    }


    public boolean isAlive(int row, int column) {
        return fields[fixVal(row, rows)][fixVal(column, columns)];
    }

    public int[] getAlive() {
        def result = []
        int offset
        for (int row = 0; row < rows; row++) {
            offset = row * columns
            for (int col = 0; col < columns; col++) {
                if (isAlive(row, col)) {
                    result << offset + col
                }
            }
        }
        return result
    }

    private int fixVal(int current, int max) {
        if (current == -1) {
            return max - 1;
        } else if (current == max) {
            return 0;
        } else {
            return current;
        }
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder()
        for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
            for (int colIdx = 0; colIdx < columns; colIdx++) {
                isAlive(rowIdx, colIdx) ? sb.append("█") : sb.append("_")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
