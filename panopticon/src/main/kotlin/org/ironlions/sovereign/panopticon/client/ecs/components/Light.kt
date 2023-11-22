package org.ironlions.sovereign.panopticon.client.ecs.components

import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex
import org.ironlions.sovereign.panopticon.client.render.shader.Program
import org.ironlions.sovereign.panopticon.client.util.Packable
import java.nio.ByteBuffer

/**
 * A light to light the scene.
 *
 * @param position The position of the light in the scene.
 * @param color The color of the light.
 * @param intensity The intensity of the light.
 */
class Light(
    override val parent: Entity,
    val position: Vec3,
    val color: Vec3,
    val intensity: Float,
) : Component(parent) {
    /** Send the lighting data to the shader. */
    fun light(program: Program) {

    }

    companion object : Packable<Light>(Light::class)
}