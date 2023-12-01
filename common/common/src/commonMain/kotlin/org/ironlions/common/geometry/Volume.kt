package org.ironlions.common.geometry

/**
 * A class to represent a rectangular area with a specific width, height, and depth. It does not
 * have a position; consider using a [Region] for this purpose. The [Volume.region] method
 * returns this, or you can call the corresponding constructor in [Region].
 *
 * @param depth The depth of the bounding box.
 * @param width The width of the bounding box.
 * @param height The height of the bounding box.
 */
class Volume(val depth: Measurement, val width: Measurement, val height: Measurement) {
    /** Convert to a [Region].
     *
     * @return The new [Region]. */
    fun region(center: Point): Region {
        return Region(center, this)
    }
}