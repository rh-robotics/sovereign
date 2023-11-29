package org.ironlions.sovereign.pathfinding.algorithms

import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

/**
 * Abstracts a path finding algorithm.
 */
interface Pathfinder {
    /** Calculate the trajectory. */
    fun pathfind(fitting: TreeFitting): List<TreeFitting.Node>?
}

/**
 * Builds a new [Pathfinder].
 */
interface PathfinderBuilder {
    /** Build the [Pathfinder]. */
    fun build(): Pathfinder
}