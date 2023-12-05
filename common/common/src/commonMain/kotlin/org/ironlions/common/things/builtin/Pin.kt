package org.ironlions.common.things.builtin

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.common.things.Thing

/**
 * An infinitely small point in space.
 *
 * @param position The position of the pin.
 * @param name The name of the pin.
 */
class Pin(position: Point, name: String = "Pin") : Thing.Concrete(
    humanName = name, region = Property.Region(
        region = Region(
            position, Volume(
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0),
                Measurement.Millimeters(1.0)
            )
        )
    )
)