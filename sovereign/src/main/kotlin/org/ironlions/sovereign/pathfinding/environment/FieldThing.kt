package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Region

/**
 * Class that represents a thing on the field
 *
 * TODO: Probably a make a LocalSpaceGroup to make defining groups of bounding boxes easier.
 *
 * @param type Defines if the field object can be moved or not (by a significant amount)
 * @param regions Defines the size and position.
 */
open class FieldThing(
    val regions: Array<Region>, val type: ThingType
)

/** The type of entity on the field. */
enum class ThingType {
    /** An immovable (in theory) entity. */
    STATIC,
    /** A dynamic (movable) entity. */
    DYNAMIC
}