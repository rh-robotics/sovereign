package org.ironlions.sovereign.geometry

import org.junit.Test
import org.junit.Assert

class Region2DTest {
    @Test
    fun testPointInsideRegion() {
        val v1 = Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0))
        val v2 = Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        val region = Region2D(v1, v2)
        val pointInside = Point2D(Measurement.Millimeters(5.0), Measurement.Millimeters(5.0))
        Assert.assertTrue(pointInside in region)
    }

    @Test
    fun testPointOutsideRegion() {
        val v1 = Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0))
        val v2 = Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        val region = Region2D(v1, v2)
        val pointOutside = Point2D(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        Assert.assertFalse(pointOutside in region)
    }

    @Test
    fun testRegionContainsAnother() {
        val region1 = Region2D(
            Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        )
        val region2 = Region2D(
            Point2D(Measurement.Millimeters(2.0), Measurement.Millimeters(2.0)),
            Point2D(Measurement.Millimeters(6.0), Measurement.Millimeters(6.0))
        )
        Assert.assertTrue(region1.contains(region2))
    }

    @Test
    fun testRegionDoesNotContainAnother() {
        val region1 = Region2D(
            Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        )
        val region2 = Region2D(
            Point2D(Measurement.Millimeters(12.0), Measurement.Millimeters(12.0)),
            Point2D(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        )
        Assert.assertFalse(region1.contains(region2))
    }

    @Test
    fun testRegionsOverlap() {
        val region1 = Region2D(
            Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        )
        val region2 = Region2D(
            Point2D(Measurement.Millimeters(5.0), Measurement.Millimeters(5.0)),
            Point2D(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        )
        Assert.assertTrue(region1.overlaps(region2))
    }

    @Test
    fun testRegionsDoNotOverlap() {
        val region1 = Region2D(
            Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(10.0))
        )
        val region2 = Region2D(
            Point2D(Measurement.Millimeters(12.0), Measurement.Millimeters(12.0)),
            Point2D(Measurement.Millimeters(15.0), Measurement.Millimeters(15.0))
        )
        Assert.assertFalse(region1.overlaps(region2))
    }

    @Test
    fun testRegionDepth() {
        val region = Region2D(
            Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(20.0))
        )
        Assert.assertTrue(region.depth == Measurement.Millimeters(10.0))
    }

    @Test
    fun testRegionWidth() {
        val region = Region2D(
            Point2D(Measurement.Millimeters(0.0), Measurement.Millimeters(0.0)),
            Point2D(Measurement.Millimeters(10.0), Measurement.Millimeters(20.0))
        )
        Assert.assertTrue(region.width == Measurement.Millimeters(20.0))
    }
}
