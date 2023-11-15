package org.ironlions.sovereign.pathfinding.fitting.tree

import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.DataFitting

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
abstract class TreeFitting(
    private val mode: TreeFittingMode,
    private val environment: Environment,
    var base: Node?,
    var goal: Node?
) : DataFitting {
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

    /**
     * Convert the Tree to a graph (GraphViz).
     */
    fun toGraphViz(): String {
        val builder = StringBuilder()

        builder.appendLine("digraph G {")
        builder.appendLine("  node [shape=record];")

        val visitedNodes = mutableSetOf<Node>()
        val queue = mutableListOf(base)

        while (queue.isNotEmpty()) {
            val currentNode = queue.removeAt(0)!!
            if (currentNode in visitedNodes) continue
            else visitedNodes.add(currentNode)

            // Define the label for the node (using the node's data)
            builder.append("  ${currentNode.hashCode()} [label=\"{${currentNode.position}}\"")

            goal?.let {
                if (currentNode.position == it.position) {
                    builder.append(", style=filled, fillcolor=red")
                }
            }

            base?.let {
                if (currentNode.position == it.position) {
                    builder.append(", style=filled, fillcolor=green")
                }
            }

            builder.appendLine("];")

            // Iterate through neighbors
            for ((neighbor, cost) in currentNode.neighbors) {
                // Create an edge between the current node and its neighbor
                builder.appendLine("  ${currentNode.hashCode()} -> ${neighbor.hashCode()} [label=\"Cost: $cost\"];")

                // If the neighbor has not been visited yet, add it to the queue for processing
                if (neighbor !in visitedNodes) {
                    queue.add(neighbor)
                }
            }
        }

        builder.appendLine("}")

        return builder.toString()
    }
}
