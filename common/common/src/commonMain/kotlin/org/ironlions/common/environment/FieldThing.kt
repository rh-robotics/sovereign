package org.ironlions.common.environment

import org.ironlions.common.geometry.Region
import org.ironlions.common.panopticon.proto.Thing
import java.util.UUID

/**
 * Class that represents a thing on the field
 *
 * TODO: Probably a make a LocalSpaceGroup to make defining groups of bounding boxes easier.
 *
 * @param region Defines the size and position.
 * @param type Defines if the field object can be moved or not (by a significant amount).
 */
abstract class FieldThing(
    val humanName: String, val region: Region, val type: ThingType
) {
    private val uuid = UUID.randomUUID()

    abstract fun serialize(): Thing
}

/** The type of entity on the field. */
enum class ThingType {
    /** An immovable (in theory) entity. */
    STATIC,

    /** A dynamic (movable) entity. */
    DYNAMIC
}