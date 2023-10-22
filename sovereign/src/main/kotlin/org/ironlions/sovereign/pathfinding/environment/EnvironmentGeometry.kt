package org.ironlions.sovereign.pathfinding.environment

import org.ironlions.sovereign.geometry.Measurement

/** Describes the geometry of the environment.
 *
 * @param fieldSideLength The side length of the field.
 */
data class EnvironmentGeometry(
    val fieldSideLength: Measurement = Measurement.Feet(12.0)
)