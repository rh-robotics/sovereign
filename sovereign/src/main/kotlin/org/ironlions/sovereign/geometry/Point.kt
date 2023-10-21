package org.ironlions.sovereign.geometry

data class Point(val x: Measurement, val y: Measurement, val z: Measurement) {
    constructor (x: Measurement, y: Measurement) : this(
        x, y, z = Measurement.Millimeters(0.0),
    )

    override fun toString(): String {
        return "(${x.millimeters}, ${y.millimeters}, ${z.millimeters})"
    }
}