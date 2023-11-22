package org.ironlions.sovereign.panopticon.client.ecs

import org.ironlions.sovereign.panopticon.client.ecs.components.Component
import org.ironlions.sovereign.panopticon.client.ecs.components.Light
import org.ironlions.sovereign.panopticon.client.ecs.components.Mesh
import org.ironlions.sovereign.panopticon.client.render.Renderer
import kotlin.reflect.KClass

/**
 * A container for entities. A scene is bound and then drawn by the psuedoengine.
 *
 * @param name The name of the scene.
 */
class Scene(
    val name: String
) {
    val entities: MutableList<Entity> = mutableListOf()
    val components: MutableMap<KClass<out Component>, MutableList<Component>> = mutableMapOf()

    /** An an entity to the scene. */
    @Suppress("UNCHECKED_CAST")
    fun add(entity: Entity) = apply {
        entities.add(entity)

        entity.components.forEach {
            components.getOrPut(it.key as KClass<out Component>) { mutableListOf() }.add(it.value)
        }
    }

    /** Remove an entity from the scene. */
    fun remove(entity: Entity) = apply { entities.remove(entity) }

    /** Draw all the entities with a mesh in the scene. */
    fun draw(renderer: Renderer) =
        components.getOrDefault(Mesh::class as KClass<out Component>, listOf())
            .forEach { (it as? Mesh)!!.draw(renderer) }

    /** Destroy the scene and all entities along with it. */
    fun destroy() = entities.forEach { entity -> entity.destroy() }
}