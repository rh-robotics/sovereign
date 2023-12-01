package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.sovereign.pathfinding.pipeline.Pipeline
import org.ironlions.sovereign.pathfinding.algorithms.AStar
import org.ironlions.common.environment.things.Pin
import org.ironlions.sovereign.pathfinding.environment.Robot
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.tree.grid.GridTreeFitter
import org.ironlions.sovereign.pathfinding.fitting.tree.grid.GridTreeFitting
import org.junit.Test
import org.junit.Assert

@Suppress("DEPRECATION")
class GridTreeFitterTest {
    @Test
    fun emptyEnvironmentTest() {
        val expected = arrayOf(
            arrayOf(false, false, false),
            arrayOf(false, false, false),
            arrayOf(false, false, false),
        )

        val pipeline = Pipeline(
            fitter = GridTreeFitter.Builder().resolution(3),
            pathfinder = AStar.Builder(),
            environment = Environment.Builder(
                Robot(
                    "Testing Robot",
                    actualizationContext = null,
                    Point(
                        x = Measurement.Feet(3.0),
                        y = Measurement.Feet(2.0),
                        z = Measurement.Feet(0.0)
                    )
                )
            )
        )

        (pipeline.fitter.fitter.fit(null) as GridTreeFitting).every { node, x, y ->
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
            fitter = GridTreeFitter.Builder().resolution(3),
            pathfinder = AStar.Builder(),
            environment = Environment.Builder(
                Robot(
                    "Testing Robot",
                    actualizationContext = null,
                    Point(
                        x = Measurement.Feet(3.0),
                        y = Measurement.Feet(2.0),
                        z = Measurement.Feet(0.0)
                    )
                )
            ).thing(
                Pin(
                    Point(
                        x = Measurement.Feet(6.0),
                        y = Measurement.Feet(6.0),
                        z = Measurement.Feet(0.0),
                    )
                )
            )
        )

        (pipeline.fitter.fitter.fit(null) as GridTreeFitting).every { node, x, y ->
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
            fitter = GridTreeFitter.Builder().resolution(3),
            pathfinder = AStar.Builder(),
            environment = Environment.Builder(
                Robot(
                    "Testing Robot",
                    actualizationContext = null,
                    Point(
                        x = Measurement.Feet(3.0),
                        y = Measurement.Feet(2.0),
                        z = Measurement.Feet(0.0)
                    )
                )
            ).thing(
                Pin(
                    Point(
                        x = Measurement.Millimeters(1.0),
                        y = Measurement.Millimeters(1.0),
                        z = Measurement.Millimeters(0.0),
                    )
                )
            )
        )

        (pipeline.fitter.fitter.fit(null) as GridTreeFitting).every { node, x, y ->
            val got = expected[x][y]
            Assert.assertNotNull(node)
            Assert.assertEquals(got, node!!.occupied)
        }
    }
}