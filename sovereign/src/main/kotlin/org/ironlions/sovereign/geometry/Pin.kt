package org.ironlions.sovereign.geometry

import org.ironlions.sovereign.pathfinding.environment.FieldThing
import org.ironlions.sovereign.pathfinding.environment.ThingType

data class Pin(val position: Point) : FieldThing(
    arrayOf(
        Region(
            position,
            Volume(
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0)
            )
        )
    ),
    ThingType.STATIC,
)