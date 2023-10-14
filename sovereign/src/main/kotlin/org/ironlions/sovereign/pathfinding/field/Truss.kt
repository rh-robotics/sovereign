package org.ironlions.sovereign.pathfinding.field

import org.ironlions.sovereign.geometry.BoundingBox
import org.ironlions.sovereign.pathfinding.FieldObject
import org.ironlions.sovereign.pathfinding.ObjectType

// TODO: (TRUSS) Change Pair & BoundingBox Values to be Accurate
// Pair values should reflect values from the roadrunner grid / coordinate system
// BoundingBox values should be measured
/**
 * Subclass of FieldObject that represents the trusses on the field
 *
 * @param position
 * @param color
 */
class Truss(position: Pair<Double, Double>, color: String): FieldObject(ObjectType.STATIC, position, BoundingBox(5.0, 5.0))