package org.ironlions.sovereign.panopticon.client.render.geometry

import glm_.vec3.Vec3
import glm_.vec4.Vec4

/** Data that is shipped off to the GPU. */
data class Vertex(val position: Vec3, val color: Vec4)

/** Contains stride data about [Vertex], freshly calculated with reflection. */
object VertexData {
    val stride: Int

    init {
        var stride = 0

        // Ooh, fancy!
        for (field in Vertex::class.java.declaredFields) {
            val name = field.type.simpleName
            stride += when (name) {
                "Vec3" -> 3 * Float.SIZE_BYTES
                "Vec4" -> 4 * Float.SIZE_BYTES
                else -> throw RuntimeException("Unknown field type in Vertex '$name'.")
            }
        }

        this.stride = stride
    }
}