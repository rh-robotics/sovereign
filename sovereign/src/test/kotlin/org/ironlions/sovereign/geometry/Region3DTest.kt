package org.ironlions.sovereign.geometry

import org.junit.Test
import org.junit.Assert

class Region3DTest {
    @Test
    fun testContainsPoint() {
        val v1 = Point3D(
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0)
        )
        val v2 = Point3D(
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0)
        )
        val region = Region3D(v1, v2)

        val pointInside = Point3D(
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0)
        )
        val pointOutside = Point3D(
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0)
        )

        Assert.assertTrue(pointInside in region)
        Assert.assertFalse(pointOutside in region)
    }

    @Test
    fun testOverlaps() {
        val v1 = Point3D(
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0)
        )
        val v2 = Point3D(
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0)
        )
        val region1 = Region3D(v1, v2)

        val v3 = Point3D(
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0)
        )
        val v4 = Point3D(
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0)
        )
        val region2 = Region3D(v3, v4)

        Assert.assertTrue(region1.overlaps(region2))
    }

    @Test
    fun testHeight() {
        val v1 = Point3D(
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0)
        )
        val v2 = Point3D(
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(20.0)
        )
        val region = Region3D(v1, v2)

        val expectedHeight = Measurement.Millimeters(20.0)
        val actualHeight = region.height

        Assert.assertTrue(expectedHeight == actualHeight)
    }
}
