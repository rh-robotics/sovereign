package org.ironlions.sovereign.panopticon.client.ecs.components

import org.ironlions.sovereign.panopticon.client.ecs.Entity

/**
 * A parent component that every component inherits from.
 *
 * @param parent The parent entity of the component.
 */
open class Component(open val parent: Entity) {
    open fun destroy() {}
}