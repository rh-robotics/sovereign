package org.ironlions.sovereign.pathfinding.algorithms

import org.ironlions.sovereign.pathfinding.fitting.TreeFitting

/** Implements the A* pathfinding algorithm. */
class AStar : Pathfinder<TreeFitting, List<Pair<Int, Int>>> {
    /** Builds a new [AStar] pathfinder. */
    class Builder : PathfinderBuilder<TreeFitting> {
        /**
         * Builds a new [AStar].
         */
        override fun build() = AStar()
    }

    /**
     * A* implementation.
     *
     * @param fitting The data source.
     * @return The calculated path.
     */
    override fun pathfind(fitting: TreeFitting): List<Pair<Int, Int>> {
        TODO("Not yet implemented")
    }

    override fun trajectory() {
        TODO("Not yet implemented")
    }
}