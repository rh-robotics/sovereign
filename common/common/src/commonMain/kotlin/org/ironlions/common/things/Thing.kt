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
abstract class Thing(
    val humanName: String,
    val adHocProperties: MutableList<Property.AdHoc> = mutableListOf(),
    val uuid: UUID = UUID.randomUUID(),
) {
    abstract class Property() {
        var note: String? = null

        class Region(val region: org.ironlions.common.geometry.Region) :
            Property()

        class Color(val r: Float, val g: Float, val b: Float) :
            Property()

        class Model(val model: String) : Property()

        class AdHoc(val data: Pair<String, String>) :
            Property()

        fun note(note: String) = apply { this.note = note }
    }

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
        val region: Property.Region,
        val color: Property.Color = Property.Color(1f, 1f, 1f),
        val model: Property.Model = Property.Model("bounding"),
        adHocProperties: MutableList<Property.AdHoc> = mutableListOf()
    ) : Thing(humanName, adHocProperties)

    /**
     * A field thing that does not exist in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param adHocProperties Other properties of this object.
     */
    open class Abstract(
        humanName: String, adHocProperties: MutableList<Property.AdHoc> = mutableListOf()
    ) : Thing(humanName, adHocProperties)
}