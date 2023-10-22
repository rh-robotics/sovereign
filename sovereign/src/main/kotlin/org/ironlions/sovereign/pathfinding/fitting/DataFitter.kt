package org.ironlions.sovereign.pathfinding.fitting

/** Abstracts a class that fits data to something a pathfinding algorithm can handle.
 *
 * @ */
interface DataFitter<R : FittingResult> {
    /**
     * Do spacial analysis and fit the data.
     *
     * @return The resulting [FittingResult].
     */
    fun fit()
}