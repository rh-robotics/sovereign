package org.ironlions.sovereign.panopticon.client.render.buffers

import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL41.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL41.GL_STATIC_DRAW
import org.lwjgl.opengl.GL41.glBufferData
import org.lwjgl.opengl.GL41.glDeleteBuffers
import org.lwjgl.opengl.GL41.glBindBuffer
import org.lwjgl.opengl.GL41.glGenBuffers
import java.nio.ByteBuffer

/** Wraps an OpenGL Vertex Buffer. */
class VertexBuffer(vertices: List<Vertex>) : BufferObject {
    private val buffer: ByteBuffer = BufferUtils.createByteBuffer(vertices.size * Vertex.stride)
    private val vbo: GenericBuffer

    init {
        // Pack the vertices into a continuous buffer.
        vertices.forEach { Vertex.pack(it, buffer) }
        buffer.flip()
        vbo = GenericBuffer(GL_ARRAY_BUFFER, buffer)
    }

    override fun bind() = vbo.bind()
    override fun unbind() = vbo.unbind()
    override fun close() = vbo.close()
}