package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.pathfinding.environment.Environment

/**
 * Abstracts a class that fits data to something a pathfinding algorithm can handle.
 *
 * @param R The type of [FittingResult].
 */
interface DataFitter<R : FittingResult> {
    /** Get the underlying [FittingResult]. */
    fun get(): R

    /**
     * Do spacial analysis and fit the data.
     *
     * @return The resulting [FittingResult].
     */
    fun fit()
}

/**
 * Builds a new [DataFitter].
 *
 * @param R The type of [FittingResult].
 */
interface DataFitterBuilder<R : FittingResult> {
    /**
     * Build the [DataFitter].
     *
     * @param environment The environment to use.
     */
    fun build(environment: Environment): DataFitter<R>
}