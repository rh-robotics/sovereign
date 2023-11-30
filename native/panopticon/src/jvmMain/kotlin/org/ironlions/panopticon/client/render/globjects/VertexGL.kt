package org.ironlions.panopticon.client.render.globjects

import org.ironlions.panopticon.client.render.geometry.Vertex
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL41.GL_ARRAY_BUFFER
import java.nio.ByteBuffer

/**
 * Wraps an OpenGL Vertex Buffer.
 *
 * @param vertices The vertices to go into the buffer.
 */
class VertexGL(vertices: List<Vertex>) : GLObject {
    private val buffer: ByteBuffer = BufferUtils.createByteBuffer(vertices.size * Vertex.stride)
    private val vbo: GenericGL

    init {
        // Pack the vertices into a continuous buffer.
        vertices.forEach { Vertex.pack(it, buffer) }
        buffer.flip()
        vbo = GenericGL(GL_ARRAY_BUFFER, buffer)
    }

    override fun bind() = vbo.bind()
    override fun unbind() = vbo.unbind()
    override fun close() = vbo.close()
}