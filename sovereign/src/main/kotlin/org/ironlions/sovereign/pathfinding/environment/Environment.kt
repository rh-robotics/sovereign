package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.pathfinding.environment.entities.Robot

/**
 * A map of the entire game field, including all field objects.
 *
 * @param robot The robot we are controlling inside the environment.
 * @param season The season to determine which default values to insert. Leave `null` for no default.
 * @param fieldSideLength The side length of the field.
 */
class Environment(
    val robot: Robot,
    val fieldSideLength: Measurement = Measurement.Feet(12.0),
    season: Season? = null,
) {
    /** All the [FieldThing]s on the field. */
    val things: MutableList<FieldThing> = ArrayList()

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