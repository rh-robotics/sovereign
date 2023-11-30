package org.ironlions.sovereign.panopticon.client.ecs

import org.ironlions.sovereign.panopticon.client.ecs.components.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * An entity is a thing within a [Scene].
 *
 * @param parent The parent scene.
 */
class Entity(val parent: Scene) {
    val components: HashMap<KClass<*>, Component> = HashMap()

    /**
     * Create a scope we can do things inside.
     *
     * @param component The component type to add.
     * @param args The arguments to pass along to the component constructor.
     */
    fun <T : Component> attachComponent(type: KClass<T>, component: T) = apply {
        components[type] = component
    }

    /**
     * Remove a component from the entity.
     *
     * @param component The component type to remove.
     */
    fun <T : Component> removeComponent(component: KClass<T>) =
        apply { components.remove(component::class) }

    fun destroy() = components.forEach {
        it.value.destroy()
    }
}