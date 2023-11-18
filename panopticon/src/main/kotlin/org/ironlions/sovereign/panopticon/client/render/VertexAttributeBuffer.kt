package org.ironlions.sovereign.panopticon.client.render

import glm_.asUnsignedLong
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.lwjgl.opengl.GL41.glBindVertexArray
import org.lwjgl.opengl.GL41.glEnableVertexAttribArray
import org.lwjgl.opengl.GL41.glVertexAttribPointer
import org.lwjgl.opengl.GL41.glGenVertexArrays

class VertexAttributeBuffer {
    private val vao = glGenVertexArrays()

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

    fun bind() = glBindVertexArray(vao)

    fun unbind() = glBindVertexArray(0)
}