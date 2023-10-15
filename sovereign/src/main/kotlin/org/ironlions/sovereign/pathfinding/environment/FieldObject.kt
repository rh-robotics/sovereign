package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Volume
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region

/**
 * Class that represents objects on the field
 *
 * @param type Defines if the field object can be moved or not (by a significant amount)
 * @param region Defines the size and position.
 */
open class FieldObject(
    val region: Region, val type: ObjectType
) {
    constructor (center: Point, volume: Volume, type: ObjectType) : this(
        region = Region(center, volume),
        type
    )
}

/**
 * Defines the two types of objects on the field, STATIC (immovable) or DYNAMIC (movable)
 */
enum class ObjectType {
    STATIC, DYNAMIC
}