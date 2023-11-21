package org.ironlions.sovereign.panopticon.client.render.geometry

import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.util.PackMe
import org.ironlions.sovereign.panopticon.client.util.Packable
import org.lwjgl.opengl.GL20.GL_FLOAT
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import java.nio.ByteBuffer

/** Data that is shipped off to the GPU. */
class Vertex(
    @PackMe
    @JvmField
    val position: Vec3,
    @PackMe
    @JvmField
    val color: Vec3,
    @PackMe
    @JvmField
    val normal: Vec3
) {
    fun bindVAO(buffer: ByteBuffer) {
        // TODO: Abstract to a VAO class and autogenerate with reflection.
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, buffer)
        glEnableVertexAttribArray(0)

        glVertexAttribPointer(
            1, 3, GL_FLOAT, false, stride, buffer.position(Float.SIZE_BYTES * 3)
        )
        glEnableVertexAttribArray(1)

        glVertexAttribPointer(
            2, 3, GL_FLOAT, false, stride, buffer.position(Float.SIZE_BYTES * 3)
        )
        glEnableVertexAttribArray(2)
    }

    companion object : Packable<Vertex>(Vertex::class)
}
