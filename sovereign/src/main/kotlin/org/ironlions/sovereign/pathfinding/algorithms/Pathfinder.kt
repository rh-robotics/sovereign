package org.ironlions.sovereign.pathfinding.algorithms

import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.DataFitter
import org.ironlions.sovereign.pathfinding.fitting.FittingResult

/**
 * Abstracts a path finding algorithm.
 */
interface Pathfinder<R : FittingResult, P> {
    /** Calculate the trajectory. */
    fun pathfind(fitting: R): P

    /** Get the calculated trajectory. */
    fun trajectory()
}

/**
 * Builds a new [DataFitter].
 */
interface PathfinderBuilder<R : FittingResult> {
    /** Build the [Pathfinder]. */
    fun build(): Pathfinder<R, *>
}