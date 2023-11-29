package org.ironlions.sovereign.panopticon.client.render.globjects

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL41.GL_STATIC_DRAW
import org.lwjgl.opengl.GL41.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL41.glBindBuffer
import org.lwjgl.opengl.GL41.glGenBuffers
import org.lwjgl.opengl.GL41.glBufferData
import org.lwjgl.opengl.GL41.glDeleteBuffers

/**
 * Indices into a list of vertices to draw triangles. Analogous to an EBO.
 *
 * @param indices The indices to use.
 */
class ElementGL(indices: List<Int>) : GLObject {
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

    override fun bind() = glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo)
    override fun unbind() = glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    override fun close() = glDeleteBuffers(ebo)
}