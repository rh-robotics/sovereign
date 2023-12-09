package org.ironlions.panopticon.client.ecs

import org.ironlions.panopticon.client.ecs.components.EcsComponent
import org.ironlions.panopticon.client.ecs.components.Mesh
import org.ironlions.panopticon.client.render.Renderer
import org.lwjgl.opengl.GL41.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL41.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL41.glClear
import kotlin.reflect.KClass

/**
 * A container for entities. A scene is bound and then drawn by the psuedoengine.
 *
 * @param name The name of the scene.
 */
class Scene(
    val name: String
) {
    private val entities: MutableMap<String, org.ironlions.panopticon.client.ecs.Component> = mutableMapOf()
    val components: MutableMap<KClass<out EcsComponent>, MutableList<EcsComponent>> = mutableMapOf()

    /** An an entity to the scene. */
    @Suppress("UNCHECKED_CAST")
    operator fun set(uuid: String, component: org.ironlions.panopticon.client.ecs.Component) = apply {
        entities[uuid] = component

        component.ecsComponents.forEach {
            components.getOrPut(it.key as KClass<out EcsComponent>) { mutableListOf() }.add(it.value)
        }
    }

    /** Remove an entity from the scene. */
    fun remove(uuid: String) = apply { entities.remove(uuid) }

    /** Draw all the entities with a mesh in the scene. */
    fun draw(renderer: Renderer) {
        renderer.activeCamera.framebuffer.bind()
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        components.getOrDefault(Mesh::class as KClass<out EcsComponent>, listOf())
            .forEach { (it as? Mesh)!!.draw(renderer) }

        renderer.activeCamera.framebuffer.unbind()
    }

    /** Destroy the scene and all entities along with it. */
    fun destroy() = entities.forEach { entity -> entity.value.destroy() }
}