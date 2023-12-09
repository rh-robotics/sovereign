package org.ironlions.sovereign.components.builtin

import org.ironlions.common.geometry.Measurement
import org.ironlions.common.geometry.Point
import org.ironlions.common.geometry.Region
import org.ironlions.common.geometry.Volume
import org.ironlions.sovereign.components.BasicComponent
import org.ironlions.sovereign.opmode.OpModeProvider

/**
 * An infinitely small point in space.
 *
 * @param position The position of the pin.
 * @param name The name of the pin.
 */
class Pin(parent: OpModeProvider, position: Point, name: String = "Pin") : BasicComponent.Concrete(
    parent = parent, humanName = name, region = Region(
        position, Volume(
            Measurement.Millimeters(1.0), Measurement.Millimeters(1.0), Measurement.Millimeters(1.0)
        )
    )
) {
    override fun init() {}

    override fun loop() {}
}