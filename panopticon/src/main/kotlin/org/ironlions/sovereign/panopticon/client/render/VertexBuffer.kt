package org.ironlions.sovereign.panopticon.client.render

import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.nio.ByteBuffer

/** Wraps an OpenGL Vertex Buffer. */
class VertexBuffer(vertices: List<Vertex>) {
    private val buffer: ByteBuffer = BufferUtils.createByteBuffer(vertices.size * Vertex.stride)
    private val vbo: Int = glGenBuffers()

    init {
        // Pack the vertices into a continuous buffer.
        vertices.forEach { it.pack(buffer) }
        buffer.flip()

        // Set up the vertex buffer.
        bind()
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        unbind()
    }

    fun bind() = glBindBuffer(GL_ARRAY_BUFFER, vbo)

    fun unbind() = glBindBuffer(GL_ARRAY_BUFFER, 0)
}