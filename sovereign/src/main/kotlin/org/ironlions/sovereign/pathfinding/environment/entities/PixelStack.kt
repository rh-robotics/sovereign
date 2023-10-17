package org.ironlions.sovereign.pathfinding.environment.entities

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point3D
import org.ironlions.sovereign.geometry.Region3D
import org.ironlions.sovereign.geometry.Volume3D
import org.ironlions.sovereign.pathfinding.environment.FieldThing
import org.ironlions.sovereign.pathfinding.environment.ThingType

/**
 * Subclass of FieldObject that represents the pixel stacks at the front of the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the pixel stack.
 */
class PixelStack(position: Point3D) : FieldThing(
    arrayOf(
        Region3D(
            position, Volume3D(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
        )
    ),
    ThingType.STATIC,
)