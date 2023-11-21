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
    companion object : Packable<Vertex>(Vertex::class)
}
