package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.pathfinding.Pipeline
import org.ironlions.sovereign.pathfinding.environment.entities.Pin
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.environment.EnvironmentGeometry
import org.ironlions.sovereign.pathfinding.environment.entities.Robot
import org.junit.Test
import org.junit.Assert

class GridFitterTest {
    @Test
    fun emptyEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
        )

        val pipeline = Pipeline(
            robot = Robot(
                Point(
                    x = Measurement.Feet(3.0), y = Measurement.Feet(2.0), z = Measurement.Feet(0.0)
                )
            ),
            dataFitter = GridFitter.Builder().resolution(3),
            environment = Environment.Builder(EnvironmentGeometry())
        )

        (pipeline.dataFitter.get() as GridFitting).every { cell, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(cell)
            Assert.assertEquals(got, cell)
        }
    }

    @Test
    fun centerEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.OCCUPIED, GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
        )

        val geometry = EnvironmentGeometry()
        val pipeline = Pipeline(
            robot = Robot(
                Point(
                    x = Measurement.Feet(3.0), y = Measurement.Feet(2.0), z = Measurement.Feet(0.0)
                )
            ),
            dataFitter = GridFitter.Builder().resolution(3),
            environment = Environment.Builder(geometry).thing(
                Pin(
                    Point(
                        x = Measurement.Fields(0.5, geometry.fieldSideLength),
                        y = Measurement.Fields(0.5, geometry.fieldSideLength),
                        z = Measurement.Fields(0.0, geometry.fieldSideLength),
                    )
                )
            )
        )

        (pipeline.dataFitter.get() as GridFitting).every { cell, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(cell)
            Assert.assertEquals(got, cell)
        }
    }

    @Test
    fun upperLeftEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(GridCell.OCCUPIED, GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
        )

        val geometry = EnvironmentGeometry()
        val pipeline = Pipeline(
            robot = Robot(
                Point(
                    x = Measurement.Feet(3.0), y = Measurement.Feet(2.0), z = Measurement.Feet(0.0)
                )
            ),
            dataFitter = GridFitter.Builder().resolution(3),
            environment = Environment.Builder(geometry).thing(
                Pin(
                    Point(
                        x = Measurement.Millimeters(1.0),
                        y = Measurement.Millimeters(1.0),
                        z = Measurement.Millimeters(0.0),
                    )
                )
            )
        )

        (pipeline.dataFitter.get() as GridFitting).every { cell, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(cell)
            Assert.assertEquals(got, cell)
        }
    }
}