package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.pathfinding.environment.entities.Robot

/**
 * A map of the entire game field, including all field objects.
 *
 * @param robot The robot we are controlling inside the environment.
 * @param geometry The geometry of the environment.
 * @param things All the [FieldThing]s on the field.
 * @param season The season to determine which default values to insert. Leave `null` for no default.
 */
class Environment(
    val robot: Robot,
    val geometry: EnvironmentGeometry = EnvironmentGeometry(),
    val things: MutableList<FieldThing> = ArrayList(),
    season: Season? = null,
) {
    /**
     * Builds a new [Environment].
     *
     * @param geometry The geometry of the environment.
     */
    class Builder(val geometry: EnvironmentGeometry) {
        /** The season to determine which default values to insert. */
        private var season: Season? = null

        /** The things ([FieldThing]) on the field. */
        private var things: MutableList<FieldThing> = ArrayList()

        /** The season to build from.
         *
         * @param season The season.
         */
        fun season(season: Season) = apply { this.season = season }

        /**
         * Add a [FieldThing] to the environment.
         *
         * @param thing The thing.
         */
        fun thing(thing: FieldThing) = apply { this.things.add(thing) }

        /**
         * Builds a new [Environment].
         *
         * @param robot The thing we control.
         */
        fun build(robot: Robot) = Environment(robot, geometry, things, season)
    }

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