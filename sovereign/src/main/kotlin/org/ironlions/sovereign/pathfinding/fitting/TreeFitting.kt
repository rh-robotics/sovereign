package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Grid
import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.pathfinding.environment.Environment

/** Different modes or conditions for the TreeFitting class. */
sealed class TreeFittingMode {
    /** Grid specifics for a [TreeFitting]. */
    data object GridMode : TreeFittingMode()
}

/**
 * A representation of an environment that certain pathfinding algorithms can use, in tree form. The
 * mode doesn't matter here.
 *
 * @param environment The environment to initially fit from.
 * @param base The root of the tree. This is the node that will be path-found from.
 */
open class TreeFitting(
    private val mode: TreeFittingMode,
    private val environment: Environment,
    var base: Node?
) : FittingResult {
    /**
     * A node inside the [TreeFitting].
     *
     * @param position the position of this node in space.
     * @param occupied Is the node occluded?
     * @param neighbors Neighbors and their associated costs.
     * @param gScore Cost from start along best known path.
     * @param fScore Estimated total cost from start to goal through this node.
     */
    data class Node(
        var position: Point,
        var occupied: Boolean = false,
        var neighbors: MutableMap<Node, Double> = mutableMapOf(),
        var gScore: Double = Double.MAX_VALUE,
        var fScore: Double = Double.MAX_VALUE,
    )
}

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
) : TreeFitting(TreeFittingMode.GridMode, environment, null) {
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

    operator fun get(row: Int): Array<Node> {
        return gridNodeRegistry[row]
    }

    val size: Int
        get() = gridNodeRegistry.size
}