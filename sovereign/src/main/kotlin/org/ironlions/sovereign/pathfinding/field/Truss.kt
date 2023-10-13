package org.ironlions.sovereign.pathfinding.field

import org.ironlions.sovereign.geometry.BoundingBox
import org.ironlions.sovereign.pathfinding.FieldObject
import org.ironlions.sovereign.pathfinding.ObjectType

// TODO: (TRUSS) Change Pair & BoundingBox Values to be Accurate
// Pair values should reflect values from the roadrunner grid / coordinate system
// BoundingBox values should be measured
class Truss(position: Pair<Double, Double>): FieldObject(ObjectType.STATIC, position, BoundingBox(5.0, 5.0)) {
}