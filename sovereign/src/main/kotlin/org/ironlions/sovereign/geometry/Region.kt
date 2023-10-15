package org.ironlions.sovereign.geometry

/**
 * A class to represent a rectangular area with a specific width and height, and position. In
 * contrast to [Volume], it has a position, and can thus be used for checking if a point is inside
 * of it.
 *
 * @param upperLeft The upper left vertex of the region.
 * @param lowerRight The lower right vertex of the region.
 */
class Region(
    val upperLeft: Point,
    val lowerRight: Point,
) {
    /** Constructs a new region from a center and a volume. */
    constructor (center: Point, volume: Volume) : this(
        center = center,
        widthRadius = Measurement.Millimeters(volume.width.millimeters / 2.0),
        heightRadius = Measurement.Millimeters(volume.height.millimeters / 2.0),
    )

    /** Constructs a new region from a center and two radix. */
    constructor (center: Point, widthRadius: Measurement, heightRadius: Measurement) : this(
        upperLeft = Point(center.x - (widthRadius / 2), center.y + (heightRadius / 2)),
        lowerRight = Point(center.x + (widthRadius / 2), center.y - (heightRadius / 2))
    )

    /** Constructs a new Region from two points, not in coordinate form.
     * @param upperLeftX The x-coordinate of the upper left vertex.
     * @param upperLeftY The y-coordinate of the upper left vertex.
     * @param lowerRightX The x-coordinate of the lower right vertex.
     * @param lowerRightY The y-coordinate of the lower right vertex.
     */
    constructor (
        upperLeftX: Measurement,
        upperLeftY: Measurement,
        lowerRightX: Measurement,
        lowerRightY: Measurement
    ) : this(
        upperLeft = Point(upperLeftX, upperLeftY),
        lowerRight = Point(lowerRightX, lowerRightY)
    )

    /** Does this region contain a point?
     * @param point The point in question.
     * @return If it is contained.
     */
    operator fun contains(point: Point): Boolean {
        val minX = upperLeft.x.millimeters.coerceAtMost(lowerRight.x.millimeters)
        val maxX = upperLeft.x.millimeters.coerceAtLeast(lowerRight.x.millimeters)
        val minY = upperLeft.y.millimeters.coerceAtMost(lowerRight.y.millimeters)
        val maxY = upperLeft.y.millimeters.coerceAtLeast(lowerRight.y.millimeters)

        return point.x.millimeters in minX..maxX && point.y.millimeters in minY..maxY
    }

    /** Does this region fully contain another?
     * @param region The region that might be fully contained.
     * @return If it is fully contained.
     */
    operator fun contains(region: Region): Boolean {
        return (upperLeft.x.millimeters <= region.upperLeft.x.millimeters &&
                upperLeft.y.millimeters <= region.upperLeft.y.millimeters &&
                lowerRight.x.millimeters >= region.lowerRight.x.millimeters &&
                lowerRight.y.millimeters >= region.lowerRight.y.millimeters)
    }

    /** Does this region overlap another?
     * @param region The region that might be overlapping this one.
     * @return If they overlap.
     */
    fun overlaps(region: Region): Boolean {
        return !(upperLeft.x.millimeters > region.lowerRight.x.millimeters ||
                lowerRight.x.millimeters < region.upperLeft.x.millimeters ||
                upperLeft.y.millimeters > region.lowerRight.y.millimeters ||
                lowerRight.y.millimeters < region.upperLeft.y.millimeters)
    }
}