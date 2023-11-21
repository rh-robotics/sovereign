package org.ironlions.sovereign.panopticon.client.ecs.components

import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.render.shader.Program
import java.nio.ByteBuffer

class Light(
    override val parent: Entity,
    val position: Vec3,
    val color: Vec3,
    val intensity: Float,
) : Component(parent) {
    fun pack(buffer: ByteBuffer) {
        // Position
        buffer.putFloat(position.x)
        buffer.putFloat(position.y)
        buffer.putFloat(position.z)

        // Color
        buffer.putFloat(color.r)
        buffer.putFloat(color.g)
        buffer.putFloat(color.b)

        // Intensity
        buffer.putFloat(intensity)
    }

    fun light(program: Program) {

    }
}