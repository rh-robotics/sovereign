package org.ironlions.panopticon.client.render.geometry

import glm_.vec3.Vec3
import org.ironlions.common.panopticon.proto.Color
import org.ironlions.common.panopticon.proto.Region

object BoundingBox {
    fun vertices(region: Region, boundingColor: Color): List<Vertex> {
        val color = Vec3(boundingColor.r, boundingColor.g, boundingColor.b)
        val normal = Vec3(0)

        return listOf(
            Vertex(Vec3(region.x1, region.y1, region.z1), color, normal),
            Vertex(Vec3(region.x2, region.y1, region.z1), color, normal),
            Vertex(Vec3(region.x2, region.y2, region.z1), color, normal),
            Vertex(Vec3(region.x1, region.y2, region.z1), color, normal),
            Vertex(Vec3(region.x1, region.y1, region.z2), color, normal),
            Vertex(Vec3(region.x2, region.y1, region.z2), color, normal),
            Vertex(Vec3(region.x2, region.y2, region.z2), color, normal),
            Vertex(Vec3(region.x1, region.y2, region.z2), color, normal),
        )
    }

    val indices = listOf(
        0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 0, 4, 1, 5, 2, 6, 3, 7
    )
}