package org.ironlions.sovereign.pathfinding.environment.entities

import org.ironlions.sovereign.geometry.Measurement
import org.ironlions.sovereign.geometry.Point
import org.ironlions.sovereign.geometry.Region
import org.ironlions.sovereign.geometry.Volume
import org.ironlions.sovereign.pathfinding.environment.ThingType
import org.ironlions.sovereign.pathfinding.environment.FieldThing

/**
 * A robot on the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the robot. */
class Robot(position: Point) :
    FieldThing(
        arrayOf(Region(
            position,
            Volume(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
        )),
        ThingType.DYNAMIC,
    )