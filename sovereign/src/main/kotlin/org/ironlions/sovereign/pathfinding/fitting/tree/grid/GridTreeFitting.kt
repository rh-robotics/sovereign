package org.ironlions.sovereign.pathfinding.fitting.tree.grid

import org.ironlions.sovereign.geometry.Grid
import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFittingMode

/**
 * A specialized version of the [TreeFitting] class for grid-based pathfinding.
 *
 * This class is used to represent an environment in a grid format for certain pathfinding algorithms.
 * It inherits from the [TreeFitting] class and adds functionality specific to grid-based pathfinding.
 *
 * @param environment The environment to initially fit from.
 * @param grid The grid to convert with.
 */
class GridTreeFitting(
    environment: Environment,
    val grid: Grid,
) : TreeFitting(TreeFittingMode.GridMode, environment, null, null) {
    /** More efficient way to keep track of which node is at which coordinate pair. */
    val gridNodeRegistry = Array(grid.tilesPerSide) { row ->
        Array(grid.tilesPerSide) { col ->
            Node(
                position = Point(
                    x = Measurement.Fields(row.toDouble()),
                    y = Measurement.Fields(col.toDouble())
                )
            )
        }
    }

    init {
        // Connect the grid nodes
        for (row in 0 until grid.tilesPerSide) {
            for (col in 0 until grid.tilesPerSide) {
                val currentNode = gridNodeRegistry[row][col]

                // Connect to the right neighbor
                if (col < grid.tilesPerSide - 1) {
                    val rightNeighbor = gridNodeRegistry[row][col + 1]
                    currentNode.neighbors[rightNeighbor] = 1.0 // TODO: weight/cost
                }

                // Connect to the bottom neighbor
                if (row < grid.tilesPerSide - 1) {
                    val bottomNeighbor = gridNodeRegistry[row + 1][col]
                    currentNode.neighbors[bottomNeighbor] = 1.0 // TODO: weight/cost
                }
            }
        }

        // Set the root node as the top-left node (0,0)
        this.base = gridNodeRegistry[0][0]
    }

    /** Loop over every cell in the [grid].
     *
     * @param fn The closure to run on every cell.
     */
    fun every(fn: (node: Node?, x: Int, y: Int) -> Unit) {
        gridNodeRegistry.forEachIndexed { xi, x ->
            x.forEachIndexed { yi, y ->
                fn(y, xi, yi)
            }
        }
    }

    operator fun get(row: Int): Array<Node> = gridNodeRegistry[row]

    val size: Int
        get() = gridNodeRegistry.size
}