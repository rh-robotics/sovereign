package org.ironlions.sovereign.geometry

import org.ironlions.sovereign.pathfinding.environment.FieldThing
import org.ironlions.sovereign.pathfinding.environment.ThingType

data class Pin2D(val position: Point2D) : FieldThing(
    arrayOf(
        Region3D(
            Point3D(
                x = position.x,
                y = position.y,
                z = Measurement.Millimeters(0.0),
            ),
            Volume3D(
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(0.0)
            )
        )
    ),
    ThingType.STATIC,
)