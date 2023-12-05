package org.ironlions.panopticon.client.util

import org.ironlions.common.geometry.Point
import org.ironlions.common.things.ThingProperty
import org.ironlions.common.geometry.Region

fun ThingProperty.Region.sensibilitize(): ThingProperty.Region = ThingProperty.Region(
    region = Region(
        v1 = Point(
            x = this.region.v1.x / 100, y = this.region.v1.y / 100, z = this.region.v1.z / 100
        ), v2 = Point(
            x = this.region.v2.x / 100, y = this.region.v2.y / 100, z = this.region.v2.z / 100
        )
    )
)
