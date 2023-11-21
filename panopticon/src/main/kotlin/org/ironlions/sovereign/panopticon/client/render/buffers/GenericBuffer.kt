package org.ironlions.sovereign.panopticon.client.render.buffers

import org.lwjgl.opengl.GL41.GL_TEXTURE_BUFFER
import org.lwjgl.opengl.GL41.GL_STATIC_DRAW
import org.lwjgl.opengl.GL41.glBindBuffer
import org.lwjgl.opengl.GL41.glDeleteBuffers
import org.lwjgl.opengl.GL41.glBufferData
import org.lwjgl.opengl.GL41.glGenBuffers
import java.nio.ByteBuffer

class GenericBuffer(private val type: Int, data: ByteBuffer) : BufferObject {
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