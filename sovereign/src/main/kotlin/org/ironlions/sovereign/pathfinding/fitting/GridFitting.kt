package org.ironlions.sovereign.pathfinding.fitting

/** A representation of an environment that certain pathfinding algorithms can use.
 * @param resolution The coarseness of the grid.
 */
class GridFitting(
    private val resolution: Int,
) : FittingResult {
    val grid: Array<Array<GridCell?>> = Array(resolution) { Array(resolution) { null } }

    init {
        (0 until resolution).forEach { x ->
            (0 until resolution).forEach { y ->
                grid[x][y] = GridCell.FREE()
            }
        }
    }

    /** Loop over every cell in the [grid]. */
    fun every(fn: (cell: GridCell?, x: Int, y: Int) -> Unit) {
        grid.forEachIndexed { xi, x ->
            x.forEachIndexed { yi, y ->
                fn(y, xi, yi)
            }
        }
    }
}

/** A cell in a [GridFitting]. */
sealed class GridCell {
    /** The cell is occupied, and not be pathfinded through. */
    data object OCCUPIED : GridCell()

    /** The cell is free, yet has an associated cost for pathfinding through. */
    data class FREE(val cost: Double = 0.5) : GridCell()
}