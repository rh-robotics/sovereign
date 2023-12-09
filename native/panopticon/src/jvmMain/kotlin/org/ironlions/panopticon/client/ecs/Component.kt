package org.ironlions.panopticon.client.ecs

import org.ironlions.panopticon.client.ecs.components.EcsComponent as EcsComponent
import java.util.UUID
import kotlin.reflect.KClass

/**
 * An component is a thing within a [Scene].
 *
 * @param parent The parent scene.
 */
class Component(
    val parent: Scene,
    val humanName: String,
    val components: List<Component>,
    val properties: MutableList<Property>,
    val abstract: Boolean,
    val uuid: UUID,
) {
    abstract class Property {
        class Region(val region: org.ironlions.common.geometry.Region) : Property()

        class Color(val r: Float, val g: Float, val b: Float) : Property()

        class Model(val model: String) : Property()

        class AdHoc(val property: Pair<String, String>): Property()

        var note: String? = null

        fun note(note: String) = apply { this.note = note }
    }

    val ecsComponents: HashMap<KClass<*>, EcsComponent> = HashMap()

    /**
     * Create a scope we can do things inside.
     *
     * @param component The component type to add.
     * @param args The arguments to pass along to the component constructor.
     */
    fun <T : EcsComponent> attachEcsComponent(type: KClass<T>, component: T) = apply {
        ecsComponents[type] = component
    }

    /**
     * Remove a component from the entity.
     *
     * @param component The component type to remove.
     */
    fun <T : EcsComponent> removeComponent(component: KClass<T>) =
        apply { ecsComponents.remove(component::class) }

    fun destroy() = ecsComponents.forEach {
        it.value.destroy()
    }
}