package org.ironlions.sovereign.pathfinding.environment.things

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.sovereign.pathfinding.environment.FieldThing
import org.ironlions.sovereign.pathfinding.environment.ThingType

/**
 * Subclass of FieldObject that represents the pixel stacks at the front of the field.
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the pixel stack.
 */
class PixelStack(position: Point) : FieldThing(
    Region(
        position, Volume(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
    ),
    ThingType.STATIC,
)