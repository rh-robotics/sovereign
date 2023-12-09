package org.ironlions.panopticon.client.render.geometry

import glm_.vec3.Vec3
import org.ironlions.panopticon.client.util.PackMe
import org.ironlions.panopticon.client.util.Packable

/**
 * Vertex geometry. data
 *
 * @param position The position of the vertex.
 * @param color The color of the vertex.
 * @param normal The normal of the vertex.
 */
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
