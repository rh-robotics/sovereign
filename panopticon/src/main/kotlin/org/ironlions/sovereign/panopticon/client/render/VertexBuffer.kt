package org.ironlions.sovereign.panopticon.client.render

import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.render.geometry.VertexData
import org.lwjgl.BufferUtils
import java.nio.ByteBuffer

/** Wraps an OpenGL Vertex Buffer. */
class VertexBuffer(vertices: List<Vertex>) {
    private val byteBuffer: ByteBuffer

    init {
        val bufferSize = vertices.size * VertexData.stride
        byteBuffer = BufferUtils.createByteBuffer(bufferSize)

        vertices.forEach { vertex ->
            // Position.
            byteBuffer.putFloat(vertex.position.x)
            byteBuffer.putFloat(vertex.position.y)
            byteBuffer.putFloat(vertex.position.z)

            // Color.
            byteBuffer.putFloat(vertex.color.r)
            byteBuffer.putFloat(vertex.color.g)
            byteBuffer.putFloat(vertex.color.b)
            byteBuffer.putFloat(vertex.color.a)
        }

        byteBuffer.flip()
    }
}