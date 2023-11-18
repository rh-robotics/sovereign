package org.ironlions.sovereign.panopticon.client.render

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL41.GL_STATIC_DRAW
import org.lwjgl.opengl.GL41.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL41.glBindBuffer
import org.lwjgl.opengl.GL41.glGenBuffers
import org.lwjgl.opengl.GL41.glBufferData

class ElementBuffer(indices: List<Int>) {
    private val buffer = BufferUtils.createIntBuffer(indices.size)
    private val ebo: Int = glGenBuffers()

    init {
        // Pack the indices
        buffer.put(indices.toIntArray()).flip()

        // Set up the buffer.
        bind()
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        unbind()
    }

    fun bind() = glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)

    fun unbind() = glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
}