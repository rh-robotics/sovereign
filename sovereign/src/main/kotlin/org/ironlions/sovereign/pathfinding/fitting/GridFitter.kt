package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.pathfinding.environment.Environment

/** A fitter that fits an environment onto a regular grid.
 * @param environment The environment to initially fit from.
 * @param resolution The coarseness of the resulting grid.
 * @param brim The birth around each field element to give, on top of the per-object value.
 */
class GridFitter(
    environment: Environment?,
    private val resolution: Int = 32,
    private val brim: Int = 0,
) : DataFitter<GridFitting> {
    val fitting = GridFitting(resolution)

    init {
        if (brim != 0) TODO("unimplemented")
        else environment?.let { fit(environment) }
    }

    override fun fit(environment: Environment) {
        val resolutionMeasurement = Measurement.Fields(
            1.toDouble() / resolution, environment.fieldSideLength
        ) as Measurement

        // Loop through every cell, and check every object.
        for (x in 0 until resolution) {
            for (y in 0 until resolution) {
                val upperLeftVertex = Point(
                    x = resolutionMeasurement * x, y = resolutionMeasurement * y
                )
                val lowerRightVertex = Point(
                    x = (resolutionMeasurement * x) + resolutionMeasurement,
                    y = (resolutionMeasurement * y) + resolutionMeasurement
                )
                val cellRegion = Region(upperLeftVertex, lowerRightVertex)

                // Look through every object and see if it overlaps with the current cell.
                for (obj in environment.objects) {
                    if (cellRegion.overlaps(obj.region)) {
                        fitting.grid[x][y] = GridCell.OCCUPIED
                        break
                    } else {
                        fitting.grid[x][y] = GridCell.FREE()
                    }
                }
            }
        }
    }
}
