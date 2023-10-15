package org.ironlions.sovereign.pathfinding.environment.objects

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.geometry.Volume
import org.ironlions.sovereign.pathfinding.environment.FieldObject
import org.ironlions.sovereign.pathfinding.environment.ObjectType

/**
 * Subclass of FieldObject that represents the pixel stacks at the front of the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position
 */
class PixelStack(position: Point) :
    FieldObject(
        Region(position, Volume(Measurement.Feet(3.0), Measurement.Feet(3.5))),
        ObjectType.STATIC,
    )