package org.ironlions.sovereign.panopticon.client.ecs.components

import glm_.vec3.Vec3
import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.ecs.Scene
import org.ironlions.sovereign.panopticon.client.render.shader.Program
import org.ironlions.sovereign.panopticon.client.util.Packable
import org.lwjgl.BufferUtils
import kotlin.reflect.KClass

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
    companion object : Packable<Light>(Light::class) {
        /**
         * Send all the lighting data to the shader.
         *
         * @param scene The scene to light.
         * @param program The shader program to send the lighting data to.
         */
        @Suppress("UNCHECKED_CAST")
        fun light(scene: Scene, program: Program) {
            val lights: List<Light> =
                scene.components.getOrDefault(Light::class as KClass<out Component>, listOf())
                    .toList() as List<Light>
            val buffer = BufferUtils.createByteBuffer(lights.size * Light.stride)

            lights.forEach { light -> Light.pack(light, buffer) }
            buffer.flip()
        }
    }
}