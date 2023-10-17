package org.ironlions.sovereign.geometry

/**
 * A class to represent a rectangular area with a specific width, height, and depth. It does not
 * have a position; consider using a [Region3D] for this purpose. The [Volume3D.region] method
 * returns this, or you can call the corresponding constructor in [Region3D].
 *
 * @param depth The depth of the bounding box.
 * @param width The width of the bounding box.
 * @param height The height of the bounding box.
 */
class Volume3D(val depth: Measurement, val width: Measurement, val height: Measurement) {
    /** Convert to a [Region3D]. */
    fun region(center: Point3D): Region3D {
        return Region3D(center, this)
    }
}