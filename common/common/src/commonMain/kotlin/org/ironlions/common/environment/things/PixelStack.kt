package org.ironlions.common.environment.things

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.common.environment.FieldThing
import org.ironlions.common.environment.ThingType
import org.ironlions.common.panopticon.proto.Thing

/**
 * Subclass of FieldObject that represents the pixel stacks at the front of the field.
 *
 * TODO: Change volume and position to be accurate.
 *
 * @param position The position of the pixel stack.
 */
class PixelStack(position: Point, name: String = "Pixel Stack") : FieldThing(
    name,
    Region(
        position, Volume(Measurement.Feet(3.0), Measurement.Feet(3.0), Measurement.Feet(3.0))
    ),
    ThingType.STATIC,
) {
    override fun serialize(): Thing = TODO("Not yet implemented")
}