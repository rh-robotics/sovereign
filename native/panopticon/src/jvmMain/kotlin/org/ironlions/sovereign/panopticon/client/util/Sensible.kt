package org.ironlions.sovereign.panopticon.client.util

import org.ironlions.proto.panopticon.environment.Region

fun Region.sensibilitize(): Region = Region(
    x1 = this.x1 / 100,
    y1 = this.y1 / 100,
    z1 = this.z1 / 100,
    x2 = this.x2 / 100,
    y2 = this.y2 / 100,
    z2 = this.z2 / 100,
)
