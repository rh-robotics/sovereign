package org.ironlions.common.environment.things

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.common.environment.FieldThing
import org.ironlions.common.environment.ThingType
import org.ironlions.common.panopticon.proto.Thing

/**
 * Subclass of FieldObject that represents the trusses on the field
 * TODO: Change volume and position to be accurate.
 * TODO: Add color.
 *
 * @param position The position of the truss.
 */
class Truss(position: Point, name: String = "Truss") : FieldThing(
    name,
    Region(
        position, Volume(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
    ),
    ThingType.STATIC,
) {
    override fun serialize(): Thing = TODO("Not yet implemented")
}