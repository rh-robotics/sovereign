package org.ironlions.common.things

abstract class ThingProperty() {
    var note: String? = null

    class Region(val region: org.ironlions.common.geometry.Region) :
        ThingProperty()

    class Color(val r: Float, val g: Float, val b: Float) :
        ThingProperty()

    class Model(val model: String) : ThingProperty()

    class AdHoc(val data: Pair<String, String>) :
        ThingProperty()

    fun note(note: String) = apply { this.note = note }
}
