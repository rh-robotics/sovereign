package org.ironlions.common.things

import java.util.UUID

/**
 * Class that represents a thing on the field
 *
 * TODO: Probably a make a LocalSpaceGroup to make defining groups of bounding boxes easier.
 *
 * @param humanName The name of this thing that is human readable.
 * @param adHocProperties Other properties of this object.
 * @param uuid The UUID used by Panopticon and some internal parts of Sovereign. **Must be UNIQUE.**
 */
abstract class FieldThing(
    val humanName: String,
    val adHocProperties: MutableList<ThingProperty.AdHoc> = mutableListOf(),
    val uuid: UUID = UUID.randomUUID(),
) {
    /**
     * A field thing that exists in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param region The region that this object occupies.
     * @param color The color of this object.
     * @param model The model of this object (display only).
     * @param adHocProperties Other properties of this object.
     */
    open class Concrete(
        humanName: String,
        val region: ThingProperty.Region,
        val color: ThingProperty.Color = ThingProperty.Color(1f, 1f, 1f),
        val model: ThingProperty.Model = ThingProperty.Model("bounding"),
        adHocProperties: MutableList<ThingProperty.AdHoc> = mutableListOf()
    ) : FieldThing(humanName, adHocProperties)

    /**
     * A field thing that does not exist in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param adHocProperties Other properties of this object.
     */
    open class Abstract(
        humanName: String, adHocProperties: MutableList<ThingProperty.AdHoc> = mutableListOf()
    ) : FieldThing(humanName, adHocProperties)
}