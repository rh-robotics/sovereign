package org.ironlions.common.environment.things

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.common.environment.FieldThing
import org.ironlions.common.environment.ThingType
import org.ironlions.common.panopticon.proto.Thing

/**
 * An infinitely small point in space.
 *
 * @param position The position of the pin.
 */
class Pin(position: Point, name: String = "Pin") : FieldThing(
    name,
    Region(
        position, Volume(
            Measurement.Millimeters(1.0), Measurement.Millimeters(1.0), Measurement.Millimeters(1.0)
        )
    ),
    ThingType.STATIC,
) {
    override fun serialize(): Thing = TODO("Not yet implemented")
}