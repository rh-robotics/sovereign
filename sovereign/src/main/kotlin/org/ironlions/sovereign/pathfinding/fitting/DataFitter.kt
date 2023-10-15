package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.pathfinding.environment.Environment

interface DataFitter<R : FittingResult> {
    /** Do spacial analysis and fit the data.
     * @param environment The environment to fit from.
     * @return The resulting [FittingResult].
     */
    fun fit(environment: Environment)
}