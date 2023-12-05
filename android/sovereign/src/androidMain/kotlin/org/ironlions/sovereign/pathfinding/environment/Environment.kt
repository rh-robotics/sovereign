package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.common.things.Thing

/**
 * A map of the entire game field, including all field objects.
 *
 * @param robot The robot we are controlling inside the environment.
 * @param things All the [Thing]s on the field.
 * @param season The season to determine which default values to insert. Leave `null` for no default.
 */
class Environment(
    val robot: Robot,
    val things: MutableList<Thing> = ArrayList(),
    season: Season? = null,
) {
    /**
     * Builds a new [Environment].
     *
     * @param robot The thing we control.
     */
    class Builder(val robot: Robot) {
        /** The season to determine which default values to insert. */
        private var season: Season? = null

        /** The things ([Thing]) on the field. */
        private var things: MutableList<Thing> = ArrayList()

        /** The season to build from.
         *
         * @param season The season.
         */
        fun season(season: Season) = apply { this.season = season }

        /**
         * Add a [Thing] to the environment.
         *
         * @param thing The thing.
         */
        fun thing(thing: Thing) = apply { this.things.add(thing) }

        /**
         * Builds a new [Environment].
         */
        fun build() = Environment(robot, things, season)
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