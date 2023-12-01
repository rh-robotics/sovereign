package org.ironlions.sovereign.geometry

import org.ironlions.common.geometry.Grid
import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
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
        val point = grid.toReal(2, 3, Measurement.Millimeters(5.0))
        Assert.assertEquals(
            Point(
                Measurement.Millimeters(40.0),
                Measurement.Millimeters(60.0),
                Measurement.Millimeters(5.0)
            ), point
        )
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