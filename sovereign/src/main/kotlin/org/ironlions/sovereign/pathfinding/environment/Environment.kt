package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.pathfinding.environment.entities.Robot

/**
 * A map of the entire game field, including all field objects.
 *
 * @param season The season to determine which default values to insert. Leave `null` for no default.
 */
class Environment(season: Season?, fieldSideLength: Double = 12.0) {
    val fieldSideLength = Measurement.Feet(fieldSideLength)
    val things: List<FieldThing> = ArrayList()
    val us: Robot = TODO()

    init {
        season?.let {
            when (it) {
                Season.CENTERSTAGE -> initializeForCenterstage()
            }
        }
    }

    /** Initialize for CENTERSTAGE (2023-2024). */
    private fun initializeForCenterstage() {
        TODO()
    }
}

/** The season to set up the environment for. */
enum class Season {
    /** The 2023-2024 season. */
    CENTERSTAGE,
}