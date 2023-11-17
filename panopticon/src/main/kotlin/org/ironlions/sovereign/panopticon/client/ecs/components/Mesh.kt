package org.ironlions.sovereign.panopticon.client.ecs.components

import org.ironlions.sovereign.panopticon.client.ecs.Entity
import org.ironlions.sovereign.panopticon.client.render.VertexBuffer
import org.ironlions.sovereign.panopticon.client.render.geometry.Vertex

/** This component allows for the rendering of an entity. */
class Mesh(override val parent: Entity, vertices: List<Vertex>) : Component(parent) {
    val buffer = VertexBuffer(vertices)

    fun draw() {
        TODO()
    }
}