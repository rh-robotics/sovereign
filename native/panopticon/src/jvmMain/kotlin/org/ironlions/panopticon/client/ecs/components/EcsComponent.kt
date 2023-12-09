package org.ironlions.panopticon.client.ecs.components

import org.ironlions.panopticon.client.ecs.Component

/**
 * A parent component that every component inherits from.
 *
 * @param parent The parent entity of the component.
 */
open class EcsComponent(open val parent: Component) {
    open fun destroy() {}
}