
package org.ironlions.sovereign.pathfinding

import org.ironlions.sovereign.geometry.BoundingBox

/**
 * Class that represents objects on the field
 *
 * @param type Defines if the field object can be moved or not (by a significant amount)
 * @param position Defines the position of the object on the field (Center point of a BoundingBox)
 * @param size Defines the size of the object on the field
 */
open class FieldObject(type: ObjectType, position: Pair<Double, Double>, size: BoundingBox) {
    private val type: ObjectType = type
    private val positon: Pair<Double, Double> = position
    private val size: BoundingBox = size
}

/**
 * Defines the two types of objects on the field, STATIC (immovable) or DYNAMIC (movable)
 */
enum class ObjectType {
    STATIC, DYNAMIC
}