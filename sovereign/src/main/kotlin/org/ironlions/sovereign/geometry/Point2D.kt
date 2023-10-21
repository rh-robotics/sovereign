package org.ironlions.sovereign.geometry

data class Point2D(val x: Measurement, val y: Measurement) {
    override fun toString(): String {
        return "($x, $y)"
    }
}