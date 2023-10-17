package org.ironlions.sovereign.pathfinding.environment.entities

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point3D
import org.ironlions.sovereign.geometry.Region3D
import org.ironlions.sovereign.geometry.Volume3D
import org.ironlions.sovereign.pathfinding.environment.FieldEntity
import org.ironlions.sovereign.pathfinding.environment.EntityType

/**
 * Subclass of FieldObject that represents the pixel stacks at the front of the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the pixel stack.
 */
class PixelStack(position: Point3D) :
    FieldEntity(
        Region3D(
            position,
            Volume3D(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
        ),
        EntityType.STATIC,
    )