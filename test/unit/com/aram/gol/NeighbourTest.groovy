package com.aram.gol

import org.junit.Test


class NeighbourTest {

    @Test
    public void testNeighbourValues() {
        assert Neighbour.NORTHWEST.dRow == -1
        assert Neighbour.NORTHWEST.dCol == -1

        assert Neighbour.NORTH.dRow == 0
        assert Neighbour.NORTH.dCol == -1

        assert Neighbour.NORTHEAST.dRow == 1
        assert Neighbour.NORTHEAST.dCol == -1

        assert Neighbour.EAST.dRow == 1
        assert Neighbour.EAST.dCol == 0

        assert Neighbour.SOUTHEAST.dRow == 1
        assert Neighbour.SOUTHEAST.dCol == 1

        assert Neighbour.SOUTH.dRow == 0
        assert Neighbour.SOUTH.dCol == 1

        assert Neighbour.SOUTHWEST.dRow == -1
        assert Neighbour.SOUTHWEST.dCol == 1

        assert Neighbour.WEST.dRow == -1
        assert Neighbour.WEST.dCol == 0
    }
}