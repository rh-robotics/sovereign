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

/**
 * This component allows for the rendering of an entity.
 *
 * @param modelMatrix The model matrix of the mesh (displacement).
 * @param program The shader program to use to draw this.
 * @param vertices The vertices that make up the mesh.
 * @param indices The triangle indices into the vertices list to draw with.
 */
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

        // Set our uniforms. TODO: this is inefficient.
        glUniformMatrix4fv(program.loc("model"), false, modelMatrix.array)
        glUniformMatrix4fv(
            program.loc("view"), false, renderer.activeCamera.getViewMatrix(renderer).array
        )
        glUniformMatrix4fv(
            program.loc("projection"), false, renderer.activeCamera.projectionMatrix.array
        )
        glUniformMatrix4fv(
            program.loc("viewPosition"), false, renderer.activeCamera.position.array
        )

        // Draw everything
        glDrawElements(GL_TRIANGLES, indices.size, GL_UNSIGNED_INT, 0)
    }

    override fun destroy() {
        attributes.close()
        buffer.close()
        elements.close()
    }
}