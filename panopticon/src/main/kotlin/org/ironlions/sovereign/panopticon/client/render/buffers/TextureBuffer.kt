package org.ironlions.sovereign.panopticon.client.render.buffers

import org.lwjgl.opengl.GL41.GL_READ_ONLY
import org.lwjgl.opengl.GL41.GL_TEXTURE_BUFFER
import org.lwjgl.opengl.GL41.glGenTextures
import org.lwjgl.opengl.GL41.glBindTexture
import org.lwjgl.opengl.GL41.glDeleteBuffers
import org.lwjgl.opengl.GL41.glTexBuffer
import org.lwjgl.opengl.GL42.glBindImageTexture
import java.nio.ByteBuffer

/**
 * A buffer holding texture data. Analogous to a TBO.
 *
 * @param type The type of texture data.
 * @param data The texture.
 */
class TextureBuffer(private val type: Int, data: ByteBuffer) : BufferObject {
    private val buffer: GenericBuffer = GenericBuffer(GL_TEXTURE_BUFFER, data)
    private val tbo: Int = glGenTextures()

    init {
        buffer.bind()
        bind()
        glTexBuffer(GL_TEXTURE_BUFFER, type, buffer.gbo)
        unbind()
        buffer.unbind()
    }

    /** Bind the TBO to an index. */
    fun bindToIndex(index: Int) = glBindImageTexture(index, tbo, 0, false, 0, GL_READ_ONLY, type)

    override fun bind() = glBindTexture(GL_TEXTURE_BUFFER, tbo)
    override fun unbind() = glBindTexture(GL_TEXTURE_BUFFER, 0)
    override fun close() {
        glDeleteBuffers(tbo)
        buffer.close()
    }
}