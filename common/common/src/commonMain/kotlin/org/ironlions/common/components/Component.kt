package org.ironlions.common.components

import java.util.UUID

/**
 * Class that represents a thing on the field. You can think of this as an entity; it wasn't called
 * an entity because Teo objected and apparently he has power even though I do all the programming
 * for this. This code will be bundled with hundreds of other changes so he won't catch this comment
 * during review, I can be almost confident of that. Can you hear me Teo? No? Ha!
 *
 * TODO: Probably a make a LocalSpaceGroup to make defining groups of bounding boxes easier.
 *
 * @param humanName The name of this thing that is human readable.
 * @param components Things that belong to this time.
 * @param adHocProperties Other properties of this object.
 * @param uuid The UUID used by Panopticon and some internal parts of Sovereign. **Must be UNIQUE.**
 */
abstract class Component(
    val humanName: String,
    val components: List<Component> = listOf(),
    val adHocProperties: MutableList<Property.AdHoc> = mutableListOf(),
    val uuid: UUID = UUID.randomUUID(),
) {
    abstract class Property() {
        var note: String? = null

        class Region(val region: org.ironlions.common.geometry.Region) : Property()

        class Color(val r: Float, val g: Float, val b: Float) : Property()

        class Model(val model: String) : Property()

        class AdHoc(val data: Pair<String, String>) : Property()

        fun note(note: String) = apply { this.note = note }
    }

    /**
     * A field thing that exists in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param region The region that this object occupies.
     * @param color The color of this object.
     * @param model The model of this object (display only).
     * @param components Things that belong to this thing.
     * @param adHocProperties Other properties of this object.
     */
    open class Concrete(
        humanName: String,
        val region: Property.Region,
        val color: Property.Color = Property.Color(1f, 1f, 1f),
        val model: Property.Model = Property.Model("bounding"),
        components: List<Component> = listOf(),
        adHocProperties: MutableList<Property.AdHoc> = mutableListOf()
    ) : Component(humanName, components, adHocProperties)

    /**
     * A field thing that does not exist in space.
     *
     * @param humanName The human-friendly name of this object.
     * @param components Things that belong to this thing.
     * @param adHocProperties Other properties of this object.
     */
    open class Abstract(
        humanName: String,
        components: List<Component> = listOf(),
        adHocProperties: MutableList<Property.AdHoc> = mutableListOf()
    ) : Component(humanName, components, adHocProperties)
}