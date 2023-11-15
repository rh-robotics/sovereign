package org.ironlions.sovereign.geometry

import kotlin.math.sqrt

/** An infinitely small point in space with a position.
 *
 * @param x The x position of the point.
 * @param y The y position of the point.
 * @param z The z position of the point.
 */
class Point(val x: Measurement, val y: Measurement, val z: Measurement) {
    /**
     * @param x The x position of the point.
     * @param y The y position of the point.
     */
    constructor (x: Measurement, y: Measurement) : this(
        x, y, z = Measurement.Millimeters(0.0),
    )

    /** Gets the string representation of this point.
     *
     * @return The string representation. */
    override fun toString(): String = "(${x.feet}, ${y.feet}, ${z.feet})"

    /** Calculates the distance from the current point to another point. */
    fun distanceTo(other: Point): Measurement {
        val dx = this.x.millimeters - other.x.millimeters
        val dy = this.y.millimeters - other.y.millimeters
        val dz = this.z.millimeters - other.z.millimeters

        val distanceSquared = dx * dx + dy * dy + dz * dz
        val distance = sqrt(distanceSquared)

        return Measurement.Millimeters(distance)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point) return false

        return this.x == other.x && this.y == other.y && this.z == other.z
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }
}