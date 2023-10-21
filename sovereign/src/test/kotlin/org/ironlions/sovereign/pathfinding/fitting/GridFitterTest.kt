package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point3D
import org.ironlions.sovereign.geometry.Pin3D
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.environment.entities.Robot
import org.junit.Test
import org.junit.Assert

class GridFitterTest {
    @Test
    fun emptyEnvironmentTest() {
        val emptyEnvironmentTestExpected = arrayOf(
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
        )

        val robot = Robot(
            Point3D(
                x = Measurement.Feet(3.0),
                y = Measurement.Feet(2.0),
                z = Measurement.Feet(0.0)
            )
        )
        val environment = Environment(robot)
        val fitter = GridFitter(environment, resolution = 3)

        fitter.fit()

        fitter.fitting.every { cell, x, y ->
            val expected = emptyEnvironmentTestExpected[x][y]
            Assert.assertNotNull(cell)
            Assert.assertEquals(expected, cell)
        }
    }

    @Test
    fun centerEnvironmentTest() {
        val centerEnvironmentTestExpected = arrayOf(
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.OCCUPIED, GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
        )

        val robot = Robot(
            Point3D(
                x = Measurement.Feet(3.0),
                y = Measurement.Feet(2.0),
                z = Measurement.Feet(0.0)
            )
        )
        val environment = Environment(robot)

        environment.things.add(
            Pin3D(
                Point3D(
                    x = Measurement.Fields(0.5, environment.fieldSideLength),
                    y = Measurement.Fields(0.5, environment.fieldSideLength),
                    z = Measurement.Fields(0.0, environment.fieldSideLength),
                )
            )
        )

        val fitter = GridFitter(environment, resolution = 3)

        fitter.fitting.every { cell, x, y ->
            val expected = centerEnvironmentTestExpected[x][y]
            Assert.assertNotNull(cell)
            Assert.assertEquals(expected, cell)
        }
    }

    @Test
    fun upperLeftEnvironmentTest() {
        val upperLeftEnvironmentTestExpected = arrayOf(
            arrayOf(GridCell.OCCUPIED, GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
            arrayOf(GridCell.FREE(), GridCell.FREE(), GridCell.FREE()),
        )

        val robot = Robot(
            Point3D(
                x = Measurement.Feet(3.0),
                y = Measurement.Feet(2.0),
                z = Measurement.Feet(0.0)
            )
        )
        val environment = Environment(robot)

        environment.things.add(
            Pin3D(
                Point3D(
                    x = Measurement.Millimeters(1.0),
                    y = Measurement.Millimeters(1.0),
                    z = Measurement.Millimeters(0.0),
                )
            )
        )

        val fitter = GridFitter(environment, resolution = 3)

        fitter.fitting.every { cell, x, y ->
            val expected = upperLeftEnvironmentTestExpected[x][y]
            Assert.assertNotNull(cell)
            Assert.assertEquals(expected, cell)
        }
    }
}