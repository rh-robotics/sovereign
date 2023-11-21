package org.ironlions.sovereign.panopticon.client.ecs

import org.ironlions.sovereign.panopticon.client.ecs.components.Mesh
import org.ironlions.sovereign.panopticon.client.render.Renderer

/** A container for entities. A scene is bound and then drawn by the psuedoengine. */
class Scene(
    val name: String
) {
    val entities: MutableList<Entity> = ArrayList()

    /** An an entity to the scene. */
    fun add(entity: Entity) = apply { entities.add(entity) }

    /** Remove an entity from the scene. */
    fun remove(entity: Entity) = apply { entities.remove(entity) }

    /** Draw all the entities with a mesh in the scene. */
    fun draw(renderer: Renderer) = entities.forEach { entity ->
        entity.components[Mesh::class]?.let { component ->
            (component as Mesh).draw(renderer)
        }
    }

    fun destroy() = entities.forEach { it.destroy() }
}