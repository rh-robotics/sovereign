package org.ironlions.sovereign.geometry

/** An infinitely small point in space with a position.
 *
 * @param x The x position of the point.
 * @param y The y position of the point.
 * @param z The z position of the point.
 */
data class Point(val x: Measurement, val y: Measurement, val z: Measurement) {
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
}