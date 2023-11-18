package org.ironlions.sovereign.panopticon.client.render.geometry

import glm_.vec3.Vec3
import glm_.vec4.Vec4
import java.nio.ByteBuffer

/** Data that is shipped off to the GPU. */
class Vertex(val position: Vec3, val color: Vec3, val normal: Vec3) {
    fun pack(buffer: ByteBuffer) {
        // Position
        buffer.putFloat(position.x)
        buffer.putFloat(position.y)
        buffer.putFloat(position.z)

        // Color
        buffer.putFloat(color.r)
        buffer.putFloat(color.g)
        buffer.putFloat(color.b)

        // Normal
        buffer.putFloat(normal.x)
        buffer.putFloat(normal.y)
        buffer.putFloat(normal.z)
    }
}

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