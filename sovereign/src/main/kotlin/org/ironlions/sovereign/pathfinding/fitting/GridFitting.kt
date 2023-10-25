package org.ironlions.sovereign.pathfinding.fitting

import org.ironlions.sovereign.geometry.Grid

/**
 * A representation of an environment that certain pathfinding algorithms can use.
 *
 * @param resolution The coarseness of the grid.
 */
class GridFitting(
    val grid: Grid,
    val robot: Pair<Int, Int>
) : FittingResult

/** A cell in a [GridFitting]. */
sealed class GridCell {
    /** The cell is occupied, and not be pathfinded through. */
    data object OCCUPIED : GridCell()

    /** The cell is free, yet has an associated cost for pathfinding through.
     *
     * @param cost The cost to navigate through this cell.
     */
    data class FREE(val cost: Double = 0.5) : GridCell()
}