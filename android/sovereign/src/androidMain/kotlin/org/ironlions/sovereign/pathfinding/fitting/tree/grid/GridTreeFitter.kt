package org.ironlions.sovereign.pathfinding.fitting.tree.grid

import org.ironlions.common.geometry.Grid
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.seasons.PrototypicalField
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.DataFitter
import org.ironlions.sovereign.pathfinding.fitting.DataFitterBuilder
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

/**
 * A fitter that fits an environment onto a regular grid.
 *
 * @param environment The environment to initially fit from.
 * @param resolution The coarseness of the resulting grid.
 * @param grid The grid to use.
 */
@Deprecated("Use of this fitter will result in inaccurate destinations. " +
        "It should be used for testing only.")
class GridTreeFitter(
    private val environment: Environment,
    private val resolution: Int = 32,
    private val grid: Grid = PrototypicalField.grid.reExpress(resolution)
) : DataFitter {
    /** Builds a new [GridTreeFitter]. */
    class Builder : DataFitterBuilder {
        /** The coarseness of the resulting grid. */
        private var resolution: Int = 32

        /**
         * The coarseness of the resulting grid.
         *
         * @param resolution The coarseness of the resulting grid.
         */
        fun resolution(resolution: Int) = apply { this.resolution = resolution }

        /**
         * Builds a new [GridTreeFitter].
         *
         * @param environment The environment to initially fit from.
         */
        override fun build(environment: Environment) = GridTreeFitter(environment, resolution)
    }

    /** The associated [TreeFitting]. */
    val fitting = GridTreeFitting(environment, grid)

    init {
        for (x in 0..<fitting.size) {
            for (y in 0..<fitting[x].size) {
                fitting[x][y].occupied = false
            }
        }

        fit(null)
    }

    /** Fit the environment into the associated [TreeFitting]. */
    override fun fit(goal: Point?): TreeFitting {
        goal?.let {
            val gridPosition = grid.toFake(it.x, it.y)
            fitting.goal = fitting[gridPosition.first][gridPosition.second]
        }

        // Loop through every cell, and check every object.
        // TODO: Leave out the last row and column, too lazy to do this now.
        fitting.gridNodeRegistry.forEachIndexed { xi, x ->
            x.forEachIndexed { yi, _ ->
                fitCell(xi, yi)
            }
        }

        return fitting
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
            v1 = PrototypicalField.grid.toReal(x, y),
            v2 = PrototypicalField.grid.toReal(x + 1, y + 1, environment.robot.geometry.height)
        )

        // TODO: Separate out dimensional collapse once more?

        // Look through every object and see if it overlaps with the current cell.
        environment.things.forEach { fitting[x][y].occupied = cellRegion.overlaps(it.geometry) }
    }
}
