package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Region3D

/**
 * Class that represents objects on the field
 *
 * @param type Defines if the field object can be moved or not (by a significant amount)
 * @param regions Defines the size and position.
 */
open class FieldEntity(
    val regions: Array<Region3D>, val type: EntityType
)

/** The type of entity on the field. */
enum class EntityType {
    /** An immovable (in theory) entity. */
    STATIC,
    /** A dynamic (movable) entity. */
    DYNAMIC
}