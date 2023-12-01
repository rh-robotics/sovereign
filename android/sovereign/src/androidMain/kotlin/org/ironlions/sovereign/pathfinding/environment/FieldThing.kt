package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.common.geometry.Region

/**
 * Class that represents a thing on the field
 *
 * TODO: Probably a make a LocalSpaceGroup to make defining groups of bounding boxes easier.
 *
 * @param regions Defines the size and position.
 * @param type Defines if the field object can be moved or not (by a significant amount)
 */
open class FieldThing(
    val geometry: Region, val type: ThingType
)

/** The type of entity on the field. */
enum class ThingType {
    /** An immovable (in theory) entity. */
    STATIC,
    /** A dynamic (movable) entity. */
    DYNAMIC
}