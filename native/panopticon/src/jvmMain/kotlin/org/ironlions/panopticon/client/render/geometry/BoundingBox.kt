package org.ironlions.panopticon.client.render.geometry

import glm_.vec3.Vec3
import org.ironlions.panopticon.client.data.Component

object BoundingBox {
    fun vertices(region: Component.Property.Region, boundingColor: Component.Property.Color): List<Vertex> {
        val color = Vec3(boundingColor.r, boundingColor.g, boundingColor.b)
        val normal = Vec3(0)
        val x1 = region.region.v1.x.millimeters
        val y1 = region.region.v1.y.millimeters
        val z1 = region.region.v1.z.millimeters
        val x2 = region.region.v2.x.millimeters
        val y2 = region.region.v2.y.millimeters
        val z2 = region.region.v2.z.millimeters

        return listOf(
            Vertex(Vec3(x1, y1, z1), color, normal),
            Vertex(Vec3(x2, y1, z1), color, normal),
            Vertex(Vec3(x2, y2, z1), color, normal),
            Vertex(Vec3(x1, y2, z1), color, normal),
            Vertex(Vec3(x1, y1, z2), color, normal),
            Vertex(Vec3(x2, y1, z2), color, normal),
            Vertex(Vec3(x2, y2, z2), color, normal),
            Vertex(Vec3(x1, y2, z2), color, normal),
        )
    }

    val indices = listOf(
        0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 0, 4, 1, 5, 2, 6, 3, 7
    )
}