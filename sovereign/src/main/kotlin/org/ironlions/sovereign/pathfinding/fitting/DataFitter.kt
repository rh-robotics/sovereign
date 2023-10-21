package org.ironlions.sovereign.pathfinding.fitting

interface DataFitter<R : FittingResult> {
    /** Do spacial analysis and fit the data.
     * @return The resulting [FittingResult].
     */
    fun fit()
}