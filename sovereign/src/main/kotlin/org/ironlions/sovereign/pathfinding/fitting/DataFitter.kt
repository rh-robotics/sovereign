package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.pathfinding.environment.Environment
import org.ironlions.sovereign.pathfinding.fitting.tree.TreeFitting

/**
 * Abstracts a class that fits data to something a pathfinding algorithm can handle.
 */
interface DataFitter {
    /**
     * Do spacial analysis and fit the data.
     *
     * @return The resulting [DataFitting].
     */
    fun fit(goal: Point?): TreeFitting
}

/**
 * Builds a new [DataFitter].
 */
interface DataFitterBuilder {
    /**
     * Build the [DataFitter].
     *
     * @param environment The environment to use.
     */
    fun build(environment: Environment): DataFitter
}