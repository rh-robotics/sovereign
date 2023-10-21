package org.ironlions.sovereign.geometry

import org.ironlions.sovereign.pathfinding.environment.FieldThing
import org.ironlions.sovereign.pathfinding.environment.ThingType

data class Pin3D(val position: Point3D) : FieldThing(
    arrayOf(
        Region3D(
            position,
            Volume3D(
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0)
            )
        )
    ),
    ThingType.STATIC,
)