package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Grid
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.pathfinding.environment.Environment

/**
 * A fitter that fits an environment onto a regular grid.
 *
 * @param environment The environment to initially fit from.
 * @param resolution The coarseness of the resulting grid.
 * @param grid The grid to use.
 */
class TreeFitter(
    private val environment: Environment,
    private val resolution: Int = 32,
    private val grid: Grid = Environment.Constants.grid.reExpress(resolution)
) : DataFitter<TreeFitting> {
    /** Builds a new [TreeFitter]. */
    class Builder : DataFitterBuilder<TreeFitting> {
        /** The coarseness of the resulting grid. */
        private var resolution: Int = 32

        /**
         * The coarseness of the resulting grid.
         *
         * @param resolution The coarseness of the resulting grid.
         */
        fun resolution(resolution: Int) = apply { this.resolution = resolution }

        /**
         * Builds a new [TreeFitter].
         *
         * @param environment The environment to initially fit from.
         */
        override fun build(environment: Environment) = TreeFitter(environment, resolution)
    }

    /** The associated [TreeFitting]. */
    val fitting = GridTreeFitting(environment, grid)

    init {
        for (x in 0..<fitting.size) {
            for (y in 0..<fitting[x].size) {
                fitting[x][y].occupied = false
            }
        }

        fit()
    }

    /** Fit the environment into the associated [TreeFitting]. */
    override fun fit() {
        // Loop through every cell, and check every object.
        // TODO: Leave out the last row and column, too lazy to do this now.
        fitting.gridNodeRegistry.forEachIndexed { xi, x ->
            x.forEachIndexed { yi, _ ->
                fitCell(xi, yi)
            }
        }
    }

    /**
     * Fit a cell into the associated [TreeFitting].
     *
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
        environment.things.forEach { fitting[x][y].occupied = cellRegion.overlaps(it.geometry) }
    }

    override fun get(): TreeFitting = fitting
}
