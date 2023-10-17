package org.ironlions.sovereign.geometry

/**
 * A class to represent a rectangular area with a specific width and height. It does not have a
 * position; consider using a [Region2D] for this purpose. The [Volume2D.region] method returns this, or
 * you can call the corresponding constructor in [Region2D].
 *
 * @param width The width of the bounding box.
 * @param height The height of the bounding box.
 */
class Volume2D(val width: Measurement, val height: Measurement) {
    /** Convert to a [Region2D]. */
    fun region(center: Point2D): Region2D {
        return Region2D(center, this)
    }
}