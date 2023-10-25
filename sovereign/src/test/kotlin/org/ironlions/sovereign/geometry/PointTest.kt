package org.ironlions.sovereign.geometry

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PointTest {
    private lateinit var grid: Grid

    @Before
    fun setUp() {
        grid = Grid(3, Measurement.Millimeters(10.0))
    }

    @Test
    fun testGetPoint() {
        val point = grid.toFake(2, 3, Measurement.Millimeters(5.0))
        Assert.assertEquals(
            Point(
                Measurement.Millimeters(40.0),
                Measurement.Millimeters(60.0),
                Measurement.Millimeters(5.0)
            ), point
        )
    }

    @Test
    fun testEvery() {
        val testData = arrayOf(
            arrayOf("A", "B", "C"), arrayOf("D", "E", "F"), arrayOf("G", "H", "I")
        )

        grid = Grid(3, Measurement.Millimeters(10.0))

        for (x in 0 until 3) {
            for (y in 0 until 3) {
                grid.grid[x][y] = testData[x][y]
            }
        }

        val results = mutableListOf<String>()
        grid.every { cell, _, _ ->
            results.add(cell?.toString() ?: "null")
        }

        val expected = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I")
        Assert.assertEquals(expected, results)
    }

    @Test
    fun testReExpress() {
        val newGrid = grid.reExpress()
        Assert.assertEquals(grid.tilesPerSide, newGrid.tilesPerSide)
        Assert.assertEquals(grid.tileSideLength, newGrid.tileSideLength)
    }

    @Test
    fun testReExpressWithNewTiles() {
        val newGrid = grid.reExpress(4)
        Assert.assertEquals(4, newGrid.tilesPerSide)
        Assert.assertEquals(13.3, newGrid.tileSideLength.millimeters, 0.3)
    }
}