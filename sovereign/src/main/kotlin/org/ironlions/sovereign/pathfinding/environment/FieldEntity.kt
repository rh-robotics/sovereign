package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Volume3D
import org.ironlions.sovereign.geometry.Point3D
import org.ironlions.sovereign.geometry.Region3D

/**
 * Class that represents objects on the field
 *
 * @param type Defines if the field object can be moved or not (by a significant amount)
 * @param region Defines the size and position.
 */
open class FieldEntity(
    val region: Region3D, val type: EntityType
) {
    constructor (center: Point3D, volume: Volume3D, type: EntityType) : this(
        region = Region3D(center, volume),
        type
    )
}

/** The type of entity on the field. */
enum class EntityType {
    /** An immovable (in theory) entity. */
    STATIC,
    /** A dynamic (movable) entity. */
    DYNAMIC
}