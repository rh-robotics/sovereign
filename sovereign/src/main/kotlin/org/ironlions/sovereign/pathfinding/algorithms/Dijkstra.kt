package org.ironlions.sovereign.pathfinding.algorithms

import org.ironlions.sovereign.pathfinding.fitting.GridCell
import org.ironlions.sovereign.pathfinding.fitting.GridFitting
import java.util.PriorityQueue

/** Implements Dijkstra's pathfinding algorithm. */
class Dijkstra : Pathfinder<GridFitting, List<Pair<Int, Int>>> {
    /** Builds a new Dijkstra pathfinder. */
    class Builder : PathfinderBuilder<GridFitting> {
        /**
         * Builds a new [Dijkstra].
         */
        override fun build() = Dijkstra()
    }

    /**
     * Dijkstra implementation.
     *
     * @param fitting The data source.
     * @return The calculated path.
     */
    override fun pathfind(fitting: GridFitting): List<Pair<Int, Int>> {
        // TODO: Take from the fitting.
        val start: Pair<Int, Int> = Pair(0, 0)  // Define the start point as (0, 0).
        val goal: Pair<Int, Int> = Pair(3, 3)   // Define the goal point as (3, 3).

        // Determine the number of rows and columns in the grid.
        val numRows = fitting.grid.grid.size
        val numCols = fitting.grid.grid[0].size

        // Initialize data structures for pathfinding.
        val dist = Array(numRows) { IntArray(numCols) { Int.MAX_VALUE } }
        val parent = Array(numRows) { IntArray(numCols) { -1 } }
        val visited = Array(numRows) { BooleanArray(numCols) }

        // Possible movement directions.
        val directions = arrayOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        // Priority queue for managing nodes to visit.
        val queue = PriorityQueue<Pair<Int, Pair<Int, Int>>>(compareBy { it.first })

        // Add the start point to the priority queue and set its distance to 0.
        queue.add(0 to start)
        dist[start.first][start.second] = 0

        // Algorithm loop.
        while (queue.isNotEmpty()) {
            val current = queue.poll()!!.second

            // If the current point is the goal, exit the loop.
            if (current == goal) break

            // If the current point has been visited, skip it.
            if (visited[current.first][current.second]) continue
            visited[current.first][current.second] = true

            // Explore neighboring points in all possible directions.
            for (dir in directions) {
                val newRow = current.first + dir.first
                val newCol = current.second + dir.second

                // Check if the neighboring point is within grid bounds and is a free cell.
                if (newRow in 0 until numRows && newCol in 0 until numCols) {
                    if (fitting.grid.grid[newRow][newCol] == GridCell.FREE()) {
                        val newDist = dist[current.first][current.second] + 1

                        // If the new distance is shorter, update the distance and add to the queue.
                        if (newDist < dist[newRow][newCol]) {
                            dist[newRow][newCol] = newDist
                            parent[newRow][newCol] = current.first * numCols + current.second
                            queue.add(newDist to (newRow to newCol))
                        }
                    }
                }
            }
        }

        // Reconstruct the path from goal to start.
        val path = mutableListOf<Pair<Int, Int>>()
        var current = goal

        while (current != start) {
            path.add(current)
            val row = current.first
            val col = current.second
            val parentIndex = parent[row][col]
            current = parentIndex / numCols to parentIndex % numCols
        }

        // Finish up.
        path.add(start)
        path.reverse()

        return path
    }

    override fun trajectory() {
        TODO("Not yet implemented")
    }
}