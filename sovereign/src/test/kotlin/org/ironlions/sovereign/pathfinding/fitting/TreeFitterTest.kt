package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.pathfinding.Pipeline
import org.ironlions.sovereign.pathfinding.algorithms.AStar
import org.ironlions.sovereign.pathfinding.environment.things.Pin
import org.ironlions.sovereign.pathfinding.environment.things.Robot
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.junit.Test
import org.junit.Assert

class TreeFitterTest {
    @Test
    fun emptyEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(false, false, false),
            arrayOf(false, false, false),
            arrayOf(false, false, false),
        )

        val pipeline = Pipeline(
            robot = Robot(
                Point(
                    x = Measurement.Feet(3.0), y = Measurement.Feet(2.0), z = Measurement.Feet(0.0)
                )
            ),
            dataFitter = TreeFitter.Builder().resolution(3),
            pathfinder = AStar.Builder(),
            environment = Environment.Builder()
        )

        (pipeline.dataFitter.get() as GridTreeFitting).every { node, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(node)
            Assert.assertEquals(got, node!!.occupied)
        }
    }

    @Test
    fun centerEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(false, false, false),
            arrayOf(false, true, false),
            arrayOf(false, false, false),
        )

        val pipeline = Pipeline(
            robot = Robot(
                Point(
                    x = Measurement.Feet(3.0), y = Measurement.Feet(2.0), z = Measurement.Feet(0.0)
                )
            ),
            dataFitter = TreeFitter.Builder().resolution(3),
            pathfinder = AStar.Builder(),
            environment = Environment.Builder().thing(
                Pin(
                    Point(
                        x = Measurement.Feet(6.0),
                        y = Measurement.Feet(6.0),
                        z = Measurement.Feet(0.0),
                    )
                )
            )
        )

        (pipeline.dataFitter.get() as GridTreeFitting).every {  node, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(node)
            Assert.assertEquals(got, node!!.occupied)
        }
    }

    @Test
    fun upperLeftEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(true, false, false),
            arrayOf(false, false, false),
            arrayOf(false, false, false),
        )

        val pipeline = Pipeline(
            robot = Robot(
                Point(
                    x = Measurement.Feet(3.0), y = Measurement.Feet(2.0), z = Measurement.Feet(0.0)
                )
            ),
            dataFitter = TreeFitter.Builder().resolution(3),
            pathfinder = AStar.Builder(),
            environment = Environment.Builder().thing(
                Pin(
                    Point(
                        x = Measurement.Millimeters(1.0),
                        y = Measurement.Millimeters(1.0),
                        z = Measurement.Millimeters(0.0),
                    )
                )
            )
        )

        (pipeline.dataFitter.get() as GridTreeFitting).every { node, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(node)
            Assert.assertEquals(got, node!!.occupied)
        }
    }
}