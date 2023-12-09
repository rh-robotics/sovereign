package org.ironlions.common.seasons

import org.ironlions.common.geometry.Grid
import org.ironlions.common.geometry.Measurement

/** Constants associated with the prototypical FTC field dimensions. */
object PrototypicalField {
    /** The side length of the field. */
    val fieldSideLength = Measurement.Feet(12.0)

    /** The side length of a field tile. */
    val fieldTileLength = Measurement.Feet(2.0)

    /** The tiles per a side of the field. */
    val tilesPerFieldSide = (fieldSideLength.feet / fieldTileLength.feet).toInt()

    /** The default field grid. */
    val grid = Grid(tilesPerFieldSide, fieldTileLength)

    init {
        /* Make sure we fit nicely. */
        assert(fieldSideLength.feet % fieldTileLength.feet == 0.0)
    }
}