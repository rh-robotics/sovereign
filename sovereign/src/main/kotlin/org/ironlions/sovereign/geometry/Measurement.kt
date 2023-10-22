package org.ironlions.sovereign.geometry

/** A measurement of some scalar value. */
sealed class Measurement {
    /** The number of millimeters in the measurement. */
    abstract val millimeters: Double

    /** An imperial inch. */
    data class Inches(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure * 25.4
    }

    /** An imperial foot. */
    data class Feet(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure * 304.8
    }

    /** An imperial yard. */
    data class Yards(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure * 914.4
    }

    /** A metric millimeter. */
    data class Millimeters(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure
    }

    /** A metric centimeter. */
    data class Centimeters(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure * 10
    }

    /** A metric decimeter. */
    data class Decimeters(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure * 100
    }

    /** A metric meter. */
    data class Meters(val measure: Double) : Measurement() {
        override val millimeters: Double
            get() = measure * 1000
    }

    /** A relative field unit. */
    data class Fields(val measure: Double, val sideLength: Measurement) : Measurement() {
        override val millimeters: Double
            get() = sideLength.millimeters * measure
    }

    /** The number of inches in the measurement. */
    val inches: Double
        get() = millimeters * 0.03937

    /** The number of feet in the measurement. */
    val feet: Double
        get() = millimeters * 0.003281

    /** The number of yards in the measurement. */
    val yards: Double
        get() = millimeters * 0.001094

    /** The number of centimeters in the measurement. */
    val centimeters: Double
        get() = millimeters / 10

    /** The number of decimeters in the measurement. */
    val decimeters: Double
        get() = millimeters / 100

    /** The number of meters in the measurement. */
    val meters: Double
        get() = millimeters / 1000

    /** `+` for measurements. */
    operator fun <T : Number> plus(d: T): Measurement {
        return Millimeters(millimeters + d.toDouble())
    }

    /** `-` for measurements. */
    operator fun <T : Number> minus(d: T): Measurement {
        return Millimeters(millimeters - d.toDouble())
    }

    /** `*` for measurements. */
    operator fun <T : Number> times(d: T): Measurement {
        return Millimeters(millimeters * d.toDouble())
    }

    /** `/` for measurements. */
    operator fun <T : Number> div(d: T): Measurement {
        return Millimeters(millimeters / d.toDouble())
    }

    /** `%` for measurements. */
    operator fun <T : Number> rem(d: T): Measurement {
        return Millimeters(millimeters % d.toDouble())
    }

    /** `+` for measurements. */
    operator fun plus(d: Measurement): Measurement {
        return Millimeters(millimeters + d.millimeters)
    }

    /** `-` for measurements. */
    operator fun minus(d: Measurement): Measurement {
        return Millimeters(millimeters - d.millimeters)
    }

    /** `*` for measurements. */
    operator fun times(d: Measurement): Measurement {
        return Millimeters(millimeters * d.millimeters)
    }

    /** `/` for measurements. */
    operator fun div(d: Measurement): Measurement {
        return Millimeters(millimeters / d.millimeters)
    }

    /** `%` for measurements. */
    operator fun rem(d: Measurement): Measurement {
        return Millimeters(millimeters % d.millimeters)
    }

    /** Compares two Measurements.
     *
     * @return The result of the comparison; -1 is `this` is lesser, 1 if greater, and 0 if the
     * same.
     */
    operator fun compareTo(z: Measurement): Int {
        return if (this.millimeters < z.millimeters) {
            -1
        } else if (this.millimeters > z.millimeters) {
            1
        } else {
            0
        }
    }

    /** Converts the Measurement into a String.
     *
     * @return The string representation.
     */
    override fun toString(): String = "$millimeters mm"
}
