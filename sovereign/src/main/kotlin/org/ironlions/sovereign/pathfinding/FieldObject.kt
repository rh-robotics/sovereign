package org.ironlions.sovereign.pathfinding

import org.ironlions.sovereign.geometry.BoundingBox

class FieldObject(type: ObjectType, position: Pair<Double, Double>, size: BoundingBox) {
    private val type: ObjectType = type
    private val positon: Pair<Double, Double> = position
    private val size: BoundingBox = size
}

enum class ObjectType {
    STATIC, DYNAMIC
}