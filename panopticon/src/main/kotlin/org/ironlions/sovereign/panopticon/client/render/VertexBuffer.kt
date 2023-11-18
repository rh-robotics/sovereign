package org.ironlions.sovereign.panopticon.client.render

import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.render.geometry.VertexData
import org.lwjgl.BufferUtils
import java.nio.ByteBuffer

/** Wraps an OpenGL Vertex Buffer. */
class VertexBuffer(vertices: List<Vertex>) {
    private val buffer: ByteBuffer

    init {
        val bufferSize = vertices.size * VertexData.stride
        buffer = BufferUtils.createByteBuffer(bufferSize)

        vertices.forEach { it.pack(buffer) }

        buffer.flip()
    }
}