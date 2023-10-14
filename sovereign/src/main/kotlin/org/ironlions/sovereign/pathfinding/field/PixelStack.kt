package org.ironlions.sovereign.pathfinding.field

import org.ironlions.sovereign.geometry.BoundingBox
import org.ironlions.sovereign.pathfinding.FieldObject
import org.ironlions.sovereign.pathfinding.ObjectType

// TODO: (PIXEL STACK) Change Pair & BoundingBox Values to be Accurate
// Pair values should reflect values from the roadrunner grid / coordinate system
// BoundingBox values should be measured
/**
 * Subclass of FieldObject that represents the pixel stacks at the front of the field
 *
 * @param position The coordinate pair position representing the center of the BoundingBox
 */

class PixelStack(position: Pair<Double, Double>): FieldObject(ObjectType.STATIC, position, BoundingBox(3.0, 3.5))