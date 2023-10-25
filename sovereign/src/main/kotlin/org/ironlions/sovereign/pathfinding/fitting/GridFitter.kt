package org.ironlions.sovereign.pathfinding.fitting

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
    /** Builds a new [GridFitter]. */
    class Builder : DataFitterBuilder<GridFitting> {
        /** The coarseness of the resulting grid. */
        private var resolution: Int = 32

        /**
         * The coarseness of the resulting grid.
         *
         * @param resolution The coarseness of the resulting grid.
         */
        fun resolution(resolution: Int) = apply { this.resolution = resolution }

        /**
         * Builds a new [GridFitter].
         *
         * @param environment The environment to initially fit from.
         */
        override fun build(environment: Environment) = GridFitter(environment, resolution)
    }

    /** The associated [GridFitting]. */
    val fitting: GridFitting

    init {
        val grid = Environment.Constants.grid.reExpress(resolution)
        fitting = GridFitting(
            grid,
            Pair(
                grid.fakeA(environment.robot.geometry.v1.x),
                grid.fakeA(environment.robot.geometry.v1.x)
            )
        )

        for (x in 0..<fitting.grid.grid.size) {
            for (y in 0..<fitting.grid.grid[x].size) {
                fitting.grid.grid[x][y] = GridCell.FREE()
            }
        }

        fit()
    }

    /** Fit the environment into the associated [GridFitting]. */
    override fun fit() {
        // Loop through every cell, and check every object.
        // TODO: Leave out the last row and column, too lazy to do this now.
        fitting.grid.grid.forEachIndexed { xi, x ->
            x.forEachIndexed { yi, _ ->
                fitCell(xi, yi)
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
        x: Int, y: Int
    ) {
        // Draw a bounding box from the ground to the top of the robot, for the cell.
        val cellRegion = Region(
            v1 = Environment.Constants.grid.toFake(x, y),
            v2 = Environment.Constants.grid.toFake(x + 1, y + 1, environment.robot.geometry.height)
        )

        // TODO: Separate out dimensional collapse once more?

        // Look through every object and see if it overlaps with the current cell.
        for (thing in environment.things) {
            // If it overlaps, the cell is occupied and may not be pathfinded through.
            println("($x, $y): $cellRegion versus ${thing.geometry}.")
            fitting.grid.grid[x][y] =
                if (cellRegion.overlaps(thing.geometry)) GridCell.OCCUPIED
                else GridCell.FREE()
        }
    }

    override fun get(): GridFitting = fitting
}
