package org.ironlions.panopticon.client.render.globjects

import glm_.asUnsignedLong
import org.ironlions.panopticon.client.render.geometry.Vertex
import org.lwjgl.opengl.GL41.glBindVertexArray
import org.lwjgl.opengl.GL41.glEnableVertexAttribArray
import org.lwjgl.opengl.GL41.glVertexAttribPointer
import org.lwjgl.opengl.GL41.glGenVertexArrays
import org.lwjgl.opengl.GL41.glDeleteVertexArrays

/** Holds attribute data about a vertex buffer. Analogous to a VAO. */
class VertexAttributeGL : GLObject {
    private val vao = glGenVertexArrays()

    /** Set up the attribute pointers. */
    fun installPointers() {
        var offset = 0

        bind()
        Vertex.pointers.forEachIndexed { i, pointer ->
            glVertexAttribPointer(
                i,
                pointer.first,
                pointer.second,
                false,
                Vertex.stride,
                offset.asUnsignedLong
            )

            glEnableVertexAttribArray(i)
            offset += pointer.first * pointer.third
        }

        unbind()
    }

    override fun bind() = glBindVertexArray(vao)

    override fun unbind() = glBindVertexArray(0)

    override fun close() = glDeleteVertexArrays(vao)
}