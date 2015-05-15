package com.aram.gol

import org.junit.Test
import static BoardUtils.*

class BoardUtilsTest {

    @Test
    public void testIsChanged() {
        Board current = new Board(rows: 2, columns: 2)
        current.init()

        Board next = new Board(rows: 2, columns: 2)
        next.init()

        assert !isChanged(current, next)

        current.setAlive(0, 0)
        assert isChanged(current, next)


        next.setAlive(0, 0)
        assert !isChanged(current, next)

    }

    @Test
    public void testIsnextGeneration() {
        assert nextGeneration([:]) == 1
        assert nextGeneration([generation: '99']) == 100
    }

}
