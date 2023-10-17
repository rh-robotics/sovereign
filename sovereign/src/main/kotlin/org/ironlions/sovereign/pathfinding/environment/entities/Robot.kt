package org.ironlions.sovereign.pathfinding.environment.entities

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point3D
import org.ironlions.sovereign.geometry.Region3D
import org.ironlions.sovereign.geometry.Volume3D
import org.ironlions.sovereign.pathfinding.environment.EntityType
import org.ironlions.sovereign.pathfinding.environment.FieldEntity

/**
 * A robot on the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the robot. */
class Robot(position: Point3D) :
    FieldEntity(
        arrayOf(Region3D(
            position,
            Volume3D(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
        )),
        EntityType.DYNAMIC,
    )