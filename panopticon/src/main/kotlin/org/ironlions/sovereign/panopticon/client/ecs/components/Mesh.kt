package org.ironlions.sovereign.panopticon.client.ecs.components

import org.ironlions.sovereign.panopticon.client.Logging
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.render.ElementBuffer
import org.ironlions.sovereign.panopticon.client.render.VertexAttributeBuffer
import org.ironlions.sovereign.panopticon.client.render.VertexBuffer
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.shader.Program
import org.lwjgl.opengl.GL41.GL_TRIANGLES
import org.lwjgl.opengl.GL41.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL41.glDrawElements

/** This component allows for the rendering of an entity. */
class Mesh(
    override val parent: Entity,
    private val program: Program,
    private val vertices: List<Vertex>,
    private val indices: List<Int>,
) : Component(parent) {
    private val attributes = VertexAttributeBuffer()
    private val buffer: VertexBuffer
    private val elements: ElementBuffer

    init {
        // Construct the necessary buffers, sans attributes.
        attributes.bind()
        buffer = VertexBuffer(vertices)
        elements = ElementBuffer(indices)

        // Bind and construct attributes.
        buffer.bind()
        elements.bind()
        attributes.installPointers()
    }

    /** Draw the mesh with OpenGL. */
    fun draw() {
        program.use()
        attributes.bind()
        glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)
    }
}