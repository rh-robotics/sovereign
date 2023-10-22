package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.pathfinding.environment.Environment

/** A fitter that fits an environment onto a regular grid.
 * @param environment The environment to initially fit from.
 * @param resolution The coarseness of the resulting grid.
 */
class GridFitter(
    private val environment: Environment,
    private val resolution: Int = 32,
) : DataFitter<GridFitting> {
    /** The associated [GridFitting]. */
    val fitting = GridFitting(resolution)

    init {
        fit()
    }

    /** Fit the environment into the associated [GridFitting]. */
    override fun fit() {
        val resolutionMeasurement = Measurement.Fields(
            1.toDouble() / resolution, environment.fieldSideLength
        ) as Measurement

        // Loop through every cell, and check every object.
        fitting.grid.forEachIndexed { xi, x ->
            x.forEachIndexed { yi, _ ->
                fitCell(resolutionMeasurement, xi, yi)
            }
        }
    }

    /**
     * Fit a cell into the associated [GridFitting].
     *
     * @param resolutionMeasurement The side length of the cell.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     */
    private fun fitCell(
        resolutionMeasurement: Measurement, x: Int, y: Int
    ) {
        for (robotRegion in environment.robot.regions) {
            // Draw a bounding box from the ground to the top of the robot, for the cell.
            val cellRegion = Region(
                v1 = Point(
                    x = resolutionMeasurement * x,
                    y = resolutionMeasurement * y,
                    z = Measurement.Millimeters(0.0)
                ), v2 = Point(
                    x = (resolutionMeasurement * x) + resolutionMeasurement,
                    y = (resolutionMeasurement * y) + resolutionMeasurement,
                    z = robotRegion.height
                )
            )

            // TODO: Separate out dimensional collapse once more?

            // Look through every object and see if it overlaps with the current cell.
            for (thing in environment.things) {
                for (thingRegion in thing.regions) {
                    // If it overlaps, the cell is occupied and may not be pathfinded through.
                    if (cellRegion.overlaps(thingRegion)) {
                        fitting.grid[x][y] = GridCell.OCCUPIED
                        return
                    }

                    fitting.grid[x][y] = GridCell.FREE()
                }
            }
        }
    }
}
