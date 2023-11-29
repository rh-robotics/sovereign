package org.ironlions.sovereign.pathfinding.algorithms

import org.ironlions.sovereign.geometry.euclideanHeuristic
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting
import java.util.PriorityQueue

/** Implements the A* pathfinding algorithm. */
class AStar(
    val heuristic: (TreeFitting.Node, TreeFitting.Node) -> Double
) : Pathfinder {
    /** Builds a new [AStar] pathfinder. */
    class Builder : PathfinderBuilder {
        /** Function to estimate the cost to reach the goal from the current node. */
        private var heuristic: (TreeFitting.Node, TreeFitting.Node) -> Double = ::euclideanHeuristic

        /**
         * Function to estimate the cost to reach the goal from the current node.
         *
         * @param heuristic The heuristic function to use.
         */
        fun heuristic(heuristic: (TreeFitting.Node, TreeFitting.Node) -> Double) = apply {
            this.heuristic = heuristic
        }

        /**
         * Builds a new [AStar].
         */
        override fun build() = AStar(heuristic)
    }

    /**
     * A* implementation.
     *
     * @param fitting The data source.
     * @return The calculated path.
     */
    override fun pathfind(fitting: TreeFitting): List<TreeFitting.Node>? {
        val openSet = PriorityQueue<TreeFitting.Node>(compareBy { it.fScore })
        val cameFrom = mutableMapOf<TreeFitting.Node, TreeFitting.Node>()
        val start = fitting.base ?: return null
        val goal = fitting.goal ?: return null

        start.gScore = 0.0
        start.fScore = heuristic(start, goal)
        openSet.add(start)

        while (openSet.isNotEmpty()) {
            val current = openSet.poll() ?: return null

            if (current == goal) return reconstructPath(cameFrom, current)

            for (neighbor in current.neighbors.keys) {
                val tentativeGScore = current.gScore + current.neighbors[neighbor]!!

                if (tentativeGScore < neighbor.gScore) {
                    cameFrom[neighbor] = current
                    neighbor.gScore = tentativeGScore
                    neighbor.fScore = tentativeGScore + heuristic(neighbor, goal)

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor)
                    }
                }
            }
        }

        return null
    }

    private fun reconstructPath(
        cameFrom: Map<TreeFitting.Node, TreeFitting.Node>,
        current: TreeFitting.Node
    ): List<TreeFitting.Node> {
        val path = mutableListOf<TreeFitting.Node>()
        var node = current

        while (cameFrom.containsKey(node)) {
            path.add(node)
            node = cameFrom[node]!!
        }

        path.add(node)
        return path.reversed()
    }
}