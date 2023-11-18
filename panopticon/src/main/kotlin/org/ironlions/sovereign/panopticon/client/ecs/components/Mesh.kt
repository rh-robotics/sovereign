package org.ironlions.sovereign.panopticon.client.ecs.components

import glm_.mat4x4.Mat4
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.render.Renderer
import org.ironlions.sovereign.panopticon.client.render.buffers.ElementBuffer
import org.ironlions.sovereign.panopticon.client.render.buffers.VertexAttributeBuffer
import org.ironlions.sovereign.panopticon.client.render.buffers.VertexBuffer
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.render.shader.Program
import org.lwjgl.opengl.GL41.GL_TRIANGLES
import org.lwjgl.opengl.GL41.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL41.glDrawElements
import org.lwjgl.opengl.GL41.glUniformMatrix4fv

/** This component allows for the rendering of an entity. */
class Mesh(
    override val parent: Entity,
    private val modelMatrix: Mat4,
    private val program: Program,
    vertices: List<Vertex>,
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
    fun draw(renderer: Renderer) {
        program.use()
        attributes.bind()

        // TODO: this is inefficient.
        glUniformMatrix4fv(program.loc("model"), false, modelMatrix.array)
        glUniformMatrix4fv(
            program.loc("view"),
            false,
            parent.parent.camera.getViewMatrix(renderer).array
        )
        glUniformMatrix4fv(
            program.loc("projection"), false, parent.parent.camera.projectionMatrix.array
        )
        glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)
    }

    override fun destroy() {
        attributes.close()
        buffer.close()
        elements.close()
    }
}