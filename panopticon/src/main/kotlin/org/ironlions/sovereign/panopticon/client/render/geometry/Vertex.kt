package org.ironlions.sovereign.panopticon.client.render.geometry

import glm_.vec3.Vec3
import org.lwjgl.opengl.GL20.GL_FLOAT
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import java.nio.ByteBuffer

/** Ignore the annotated target when computing the vertex stride. */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class IgnoreInStride

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

    /** Contains stride data about [Vertex], freshly calculated with reflection. */
    @IgnoreInStride
    companion object {
        @IgnoreInStride
        val stride: Int

        @IgnoreInStride
        val pointers: MutableList<Triple<Int, Int, Int>> = ArrayList()

        init {
            var stride = 0

            // Ooh, fancy!
            for (field in Vertex::class.java.declaredFields) {
                if (field.isAnnotationPresent(IgnoreInStride::class.java)) continue
                if (field.type.simpleName == "Companion") continue

                val triple = when (field.type.simpleName) {
                    "Vec3" -> Triple(3, GL_FLOAT, Float.SIZE_BYTES)
                    "Vec4" -> Triple(4, GL_FLOAT, Float.SIZE_BYTES)
                    else -> throw RuntimeException("Unknown field type in Vertex '${field.type.simpleName}'.")
                }

                pointers.add(triple)
                stride += triple.first * triple.third
            }

            this.stride = stride
        }

        fun bindVAO(buffer: ByteBuffer) {
            // TODO: Abstract to a VAO class and autogenerate with reflection.
            glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, buffer)
            glEnableVertexAttribArray(0)

            glVertexAttribPointer(
                1, 3, GL_FLOAT, false, stride, buffer.position(Float.SIZE_BYTES * 3)
            )
            glEnableVertexAttribArray(1)

            glVertexAttribPointer(
                2, 3, GL_FLOAT, false, stride, buffer.position(Float.SIZE_BYTES * 3)
            )
            glEnableVertexAttribArray(2)
        }
    }
}
