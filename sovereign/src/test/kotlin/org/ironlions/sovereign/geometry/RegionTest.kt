package org.ironlions.sovereign.geometry

import org.junit.Test
import org.junit.Assert

class RegionTest {
    @Test
    fun testContainsPoint() {
        val v1 = Point(
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0)
        )
        val v2 = Point(
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0)
        )
        val region = Region(v1, v2)

        val pointInside = Point(
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0)
        )
        val pointOutside = Point(
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0)
        )

        Assert.assertTrue(pointInside in region)
        Assert.assertFalse(pointOutside in region)
    }

    @Test
    fun testOverlaps() {
        val v1 = Point(
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0)
        )
        val v2 = Point(
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0)
        )
        val region1 = Region(v1, v2)

        val v3 = Point(
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0),
            Measurement.Millimeters(5.0)
        )
        val v4 = Point(
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0),
            Measurement.Millimeters(15.0)
        )
        val region2 = Region(v3, v4)

        Assert.assertTrue(region1.overlaps(region2))
    }

    @Test
    fun testHeight() {
        val v1 = Point(
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0),
            Measurement.Millimeters(0.0)
        )
        val v2 = Point(
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(10.0),
            Measurement.Millimeters(20.0)
        )
        val region = Region(v1, v2)

        val expectedHeight = Measurement.Millimeters(20.0)
        val actualHeight = region.height

        Assert.assertTrue(expectedHeight == actualHeight)
    }

    @Test
    fun testPointInsideRegion() {
        val v1 = Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0))
        val v2 = Point(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        val region = Region(v1, v2)
        val pointInside = Point(Measurement.Millimeters(5.0), Measurement.Millimeters(5.0))
        Assert.assertTrue(pointInside in region)
    }

    @Test
    fun testPointOutsideRegion() {
        val v1 = Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0))
        val v2 = Point(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        val region = Region(v1, v2)
        val pointOutside = Point(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        Assert.assertFalse(pointOutside in region)
    }

    @Test
    fun testRegionsOverlap() {
        val region1 = Region(
            Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        )
        val region2 = Region(
            Point(Measurement.Millimeters(5.0), Measurement.Millimeters(5.0)),
            Point(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        )
        Assert.assertTrue(region1.overlaps(region2))
    }

    @Test
    fun testRegionsDoNotOverlap() {
        val region1 = Region(
            Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        )
        val region2 = Region(
            Point(Measurement.Millimeters(12.0), Measurement.Millimeters(12.0)),
            Point(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        )
        Assert.assertFalse(region1.overlaps(region2))
    }

    @Test
    fun testRegionDepth() {
        val region = Region(
            Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point(Measurement.Millimeters(10.0), Measurement.Millimeters(20.0))
        )
        Assert.assertTrue(region.depth == Measurement.Millimeters(10.0))
    }

    @Test
    fun testRegionWidth() {
        val region = Region(
            Point(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point(Measurement.Millimeters(10.0), Measurement.Millimeters(20.0))
        )
        Assert.assertTrue(region.width == Measurement.Millimeters(20.0))
    }
}
