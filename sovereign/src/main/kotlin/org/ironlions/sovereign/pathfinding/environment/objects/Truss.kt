package org.ironlions.sovereign.pathfinding.environment.objects

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.geometry.Volume
import org.ironlions.sovereign.pathfinding.environment.FieldObject
import org.ironlions.sovereign.pathfinding.environment.ObjectType

/**
 * Subclass of FieldObject that represents the trusses on the field
 * TODO: Change volume and position to be accurate.
 * TODO: Add color.
 *
 * @param position
 */
class Truss(position: Point) :
    FieldObject(
        Region(position, Volume(Measurement.Feet(5.0), Measurement.Feet(5.0))),
        ObjectType.STATIC,
    )