package org.ironlions.sovereign.pathfinding.environment.things

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.sovereign.pathfinding.environment.FieldThing
import org.ironlions.sovereign.pathfinding.environment.ThingType

/**
 * An infinitely small point in space.
 *
 * @param position The position of the pin.
 */
data class Pin(val position: Point) : FieldThing(
    Region(
        position,
        Volume(
            Measurement.Millimeters(1.0),
            Measurement.Millimeters(1.0),
            Measurement.Millimeters(1.0)
        )
    ),
    ThingType.STATIC,
)