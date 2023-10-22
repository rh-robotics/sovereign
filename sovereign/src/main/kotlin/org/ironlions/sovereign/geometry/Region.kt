package org.ironlions.sovereign.geometry

import android.icu.util.Measure
import org.ironlions.sovereign.math.rangesOverlap

/**
 * A class to represent a rectangular area with a specific depth width, height, and position. In
 * contrast to [Volume], it has a position, and can thus be used for checking if a point is inside
 * of it.
 *
 * @param v1 The upper left vertex of the region.
 * @param v2 The lower right vertex of the region.
 */
class Region(
    val v1: Point,
    val v2: Point,
) {
    /** Constructs a new region from a center and a volume. */
    constructor (center: Point, volume: Volume) : this(
        center = center,
        depthRadius = Measurement.Millimeters(volume.depth.millimeters / 2.0),
        widthRadius = Measurement.Millimeters(volume.width.millimeters / 2.0),
        heightRadius = Measurement.Millimeters(volume.height.millimeters / 2.0),
    )

    /** Constructs a new region from a center and three radix.
     *
     * @param center The center of the region.
     * @param depthRadius Half of the depth.
     * @param widthRadius Half of the width.
     * @param heightRadius Half of the height.
     */
    constructor (
        center: Point,
        depthRadius: Measurement,
        widthRadius: Measurement,
        heightRadius: Measurement
    ) : this(
        v1 = Point(
            x = center.x - (depthRadius / 2),
            y = center.y + (widthRadius / 2),
            z = center.z + (heightRadius / 2),
        ), v2 = Point(
            x = center.x + (widthRadius / 2),
            y = center.y - (heightRadius / 2),
            z = center.z - (heightRadius / 2),
        )
    )

    /** Constructs a new region from a center and two radix.
     *
     * @center The center of the region.
     * @param depthRadius Half of the depth.
     * @param widthRadius Half of the width.
     */
    constructor (
        center: Point,
        depthRadius: Measurement,
        widthRadius: Measurement,
    ) : this(
        v1 = Point(
            x = center.x - (depthRadius / 2),
            y = center.y + (widthRadius / 2),
            z = Measurement.Millimeters(0.0),
        ), v2 = Point(
            x = center.x + (depthRadius / 2),
            y = center.y - (widthRadius / 2),
            z = Measurement.Millimeters(0.0),
        )
    )

    /** Does this region contain a point?
     *
     * @param point The point in question.
     * @return If it is contained.
     */
    operator fun contains(point: Point): Boolean {
        val minX = v1.x.millimeters.coerceAtMost(v2.x.millimeters)
        val maxX = v1.x.millimeters.coerceAtLeast(v2.x.millimeters)
        val minY = v1.y.millimeters.coerceAtMost(v2.y.millimeters)
        val maxY = v1.y.millimeters.coerceAtLeast(v2.y.millimeters)

        return point.x.millimeters in minX..maxX && point.y.millimeters in minY..maxY
    }

    /** Does this region overlap another?
     *
     * @param region The region that might be overlapping this one.
     * @return If they overlap.
     */
    fun overlaps(region: Region): Boolean {
        val xOverlap = rangesOverlap(
            range1 = region.v1.x.millimeters..region.v2.x.millimeters,
            range2 = this.v1.x.millimeters..this.v2.x.millimeters
        )

        val yOverlap = rangesOverlap(
            range1 = region.v1.y.millimeters..region.v2.y.millimeters,
            range2 = this.v1.y.millimeters..this.v2.y.millimeters
        )

        val zOverlap = rangesOverlap(
            range1 = region.v1.z.millimeters..region.v2.z.millimeters,
            range2 = this.v1.z.millimeters..this.v2.z.millimeters
        )

        return zOverlap && yOverlap && xOverlap
    }

    /** Returns the string representation of this region.
     *
     * @return The string representation. */
    override fun toString(): String {
        return "Region3D(v1=$v1, v2=$v2)"
    }

    /** Gets the depth of the region.
     *
     * @return The region height.
     */
    val depth: Measurement
        get() = if (v1.x > v2.x) v1.x else v2.x

    /** Gets the width of the region.
     *
     * @return The region height.
     */
    val width: Measurement
        get() = if (v1.y > v2.y) v1.y else v2.y

    /** Gets the height of the region.
     *
     * @return The region height.
     */
    val height: Measurement
        get() = if (v1.z > v2.z) v1.z else v2.z
}