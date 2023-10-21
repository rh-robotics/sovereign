package org.ironlions.sovereign.geometry

data class Point3D(val x: Measurement, val y: Measurement, val z: Measurement) {
    override fun toString(): String {
        return "(${x.millimeters}, ${y.millimeters}, ${z.millimeters})"
    }
}