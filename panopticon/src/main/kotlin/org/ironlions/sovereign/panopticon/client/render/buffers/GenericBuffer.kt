package org.ironlions.sovereign.panopticon.client.render.buffers

import org.lwjgl.opengl.GL41.GL_STATIC_DRAW
import org.lwjgl.opengl.GL41.glBindBuffer
import org.lwjgl.opengl.GL41.glDeleteBuffers
import org.lwjgl.opengl.GL41.glBufferData
import org.lwjgl.opengl.GL41.glGenBuffers
import java.nio.ByteBuffer

/**
 * A generic buffer representing data on the GPU.
 *
 * @param type The type of buffer.
 * @param data The data inside the buffer.
 */
class GenericBuffer(private val type: Int, data: ByteBuffer) : BufferObject {
    /** The underlying OpenGL object. */
    val gbo: Int = glGenBuffers()

    init {
        bind()
        glBufferData(type, data, GL_STATIC_DRAW)
        unbind()
    }

    override fun bind() = glBindBuffer(type, gbo)
    override fun unbind() = glBindBuffer(type, 0)
    override fun close() = glDeleteBuffers(gbo)
}