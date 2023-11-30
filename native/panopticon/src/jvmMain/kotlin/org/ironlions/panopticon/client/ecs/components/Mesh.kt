package org.ironlions.panopticon.client.ecs.components

import glm_.mat4x4.Mat4
import org.ironlions.panopticon.client.ecs.Entity
import org.ironlions.panopticon.client.render.Renderer
import org.ironlions.panopticon.client.render.globjects.ElementGL
import org.ironlions.panopticon.client.render.globjects.VertexAttributeGL
import org.ironlions.panopticon.client.render.globjects.VertexGL
import org.ironlions.panopticon.client.render.geometry.Vertex
import org.ironlions.panopticon.client.render.shader.Program
import org.lwjgl.opengl.GL41.GL_TRIANGLES
import org.lwjgl.opengl.GL41.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL41.glDrawArrays
import org.lwjgl.opengl.GL41.glDrawElements
import org.lwjgl.opengl.GL41.glUniformMatrix4fv
import org.lwjgl.opengl.GL41.glUniform1f

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
    private val vertices: List<Vertex>,
    private val indices: List<Int>?,
    private val type: Int = GL_TRIANGLES
) : Component(parent) {
    private val attributes = VertexAttributeGL()
    private val buffer: VertexGL
    private val elements: ElementGL?

    init {
        // Construct the necessary buffers, sans attributes.
        attributes.bind()
        buffer = VertexGL(vertices)

        elements = if (indices != null) ElementGL(indices)
        else null

        // Bind and construct attributes.
        buffer.bind()
        elements?.bind()
        attributes.installPointers()
    }

    /** Draw the mesh with OpenGL. */
    fun draw(renderer: Renderer) {
        program.use()
        attributes.bind()

        // Set our uniforms. TODO: this is inefficient.
        glUniformMatrix4fv(program.loc("model"), false, modelMatrix.array)
        glUniformMatrix4fv(
            program.loc("view"), false, renderer.activeCamera.getViewMatrix().array
        )
        glUniformMatrix4fv(
            program.loc("projection"), false, renderer.activeCamera.projectionMatrix.array
        )
        glUniformMatrix4fv(
            program.loc("viewPosition"), false, renderer.activeCamera.position.array
        )

        if (type != GL_TRIANGLES) glUniform1f(program.loc("isComplex"), 1.0f)
        else glUniform1f(program.loc("isComplex"), 0.0f)

        // Draw everything
        if (indices != null) glDrawElements(type, indices.size, GL_UNSIGNED_INT, 0)
        else glDrawArrays(type, 0, vertices.size)
    }

    override fun destroy() {
        attributes.close()
        buffer.close()
        elements?.close()
    }
}