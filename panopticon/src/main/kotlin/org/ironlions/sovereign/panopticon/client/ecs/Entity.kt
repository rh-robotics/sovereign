package org.ironlions.sovereign.panopticon.client.ecs

import org.ironlions.sovereign.panopticon.client.ecs.components.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.primaryConstructor

class Entity(val parent: Scene) {
    val components: HashMap<KClass<*>, Component> = HashMap()

    /** Creates a scope we can do things inside. */
    fun <T : Component> addComponent(component: KClass<T>, init: T.() -> Unit) {
        val instance = component.primaryConstructor!!.call(this)
        instance.init()
        components[component] = instance
    }

    /** Remove a component from the entity. */
    fun remove(component: Component) = apply { components.remove(component::class) }
}