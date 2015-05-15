package com.aram.gol;

public enum Neighbour {
    NORTHWEST(-1, -1),
    NORTH(0, -1),
    NORTHEAST(1, -1),
    EAST(1, 0),
    SOUTHEAST(1, 1),
    SOUTH(0, 1),
    SOUTHWEST(-1, 1),
    WEST(-1, 0);

    final int dRow;
    final int dCol;

    Neighbour(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }
}
