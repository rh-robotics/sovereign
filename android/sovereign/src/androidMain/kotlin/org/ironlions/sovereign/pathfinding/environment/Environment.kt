package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.components.BasicComponent

/**
 * A map of the entire game field, including all field objects.
 *
 * @param robot The robot we are controlling inside the environment.
 * @param components All the [BasicComponent]s on the field.
 * @param season The season to determine which default values to insert. Leave `null` for no default.
 */
class Environment(
    val robot: Robot,
    val components: MutableList<BasicComponent> = ArrayList(),
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

        /** The things ([BasicComponent]) on the field. */
        private var components: MutableList<BasicComponent> = ArrayList()

        /** The season to build from.
         *
         * @param season The season.
         */
        fun season(season: Season) = apply { this.season = season }

        /**
         * Add a [BasicComponent] to the environment.
         *
         * @param component The component.
         */
        fun component(component: BasicComponent) = apply { this.components.add(component) }

        /**
         * Builds a new [Environment].
         */
        fun build() = Environment(robot, components, season)
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