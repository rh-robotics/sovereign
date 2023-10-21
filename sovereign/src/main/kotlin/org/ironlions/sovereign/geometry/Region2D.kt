package org.ironlions.sovereign.geometry

/**
 * A class to represent a rectangular area with a specific width and height, and position. In
 * contrast to [Volume2D], it has a position, and can thus be used for checking if a point is inside
 * of it.
 *
 * @param v1 The upper left vertex of the region.
 * @param v2 The lower right vertex of the region.
 */
class Region2D(
    val v1: Point2D,
    val v2: Point2D,
) {
    /** Constructs a new region from a center and a volume. */
    constructor (center: Point2D, volume: Volume2D) : this(
        center = center,
        depthRadius = Measurement.Millimeters(volume.width.millimeters / 2.0),
        widthRadius = Measurement.Millimeters(volume.height.millimeters / 2.0),
    )

    /** Constructs a new region from a center and two radix. */
    constructor (center: Point2D, depthRadius: Measurement, widthRadius: Measurement) : this(
        v1 = Point2D(center.x - (depthRadius / 2), center.y + (widthRadius / 2)),
        v2 = Point2D(center.x + (depthRadius / 2), center.y - (widthRadius / 2))
    )

    /** Does this region contain a point?
     * @param point The point in question.
     * @return If it is contained.
     */
    operator fun contains(point: Point2D): Boolean {
        val minX = v1.x.millimeters.coerceAtMost(v2.x.millimeters)
        val maxX = v1.x.millimeters.coerceAtLeast(v2.x.millimeters)
        val minY = v1.y.millimeters.coerceAtMost(v2.y.millimeters)
        val maxY = v1.y.millimeters.coerceAtLeast(v2.y.millimeters)

        return point.x.millimeters in minX..maxX && point.y.millimeters in minY..maxY
    }

    /** Does this region fully contain another?
     * @param region The region that might be fully contained.
     * @return If it is fully contained.
     */
    operator fun contains(region: Region2D): Boolean {
        return (v1.x.millimeters <= region.v1.x.millimeters &&
                v1.y.millimeters <= region.v1.y.millimeters &&
                v2.x.millimeters >= region.v2.x.millimeters &&
                v2.y.millimeters >= region.v2.y.millimeters)
    }

    /** Does this region overlap another?
     * @param region The region that might be overlapping this one.
     * @return If they overlap.
     */
    fun overlaps(region: Region2D): Boolean {
        return !(v1.x.millimeters > region.v2.x.millimeters ||
                v2.x.millimeters < region.v1.x.millimeters ||
                v1.y.millimeters > region.v2.y.millimeters ||
                v2.y.millimeters < region.v1.y.millimeters)
    }

    override fun toString(): String {
        return "Region3D(v1=$v1, v2=$v2)"
    }

    /** Gets the depth of the region.
     * @return The region height
     */
    val depth: Measurement
        get() = if (v1.x > v2.x) v1.x else v2.x

    /** Gets the width of the region.
     * @return The region height
     */
    val width: Measurement
        get() = if (v1.y > v2.y) v1.y else v2.y
}