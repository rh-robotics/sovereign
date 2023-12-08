package org.ironlions.panopticon.client.data

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
 * @param properties Other properties of this object.
 * @param abstract Whether this object does not exist in physical space.
 * @param uuid The UUID used by Panopticon and some internal parts of Sovereign. **Must be UNIQUE.**
 */
abstract class Component(
    val humanName: String,
    val components: List<Component>,
    val properties: MutableList<Property>,
    val abstract: Boolean,
    val uuid: UUID,
) {
    abstract class Property {
        class Region(val region: org.ironlions.common.geometry.Region) : Property()

        class Color(val r: Float, val g: Float, val b: Float) : Property()

        class Model(val model: String) : Property()

        class AdHoc(val property: Pair<String, String>): Property()

        var note: String? = null

        fun note(note: String) = apply { this.note = note }
    }
}
