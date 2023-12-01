package org.ironlions.sovereign.geometry

import org.ironlions.common.geometry.Measurement
import org.junit.Test
import org.junit.Assert

class MeasurementTest {
    @Test
    fun testInchesToMillimetersConversion() {
        val inchesMeasurement = Measurement.Inches(1.0)
        Assert.assertEquals(25.4, inchesMeasurement.millimeters, 0.001)
    }

    @Test
    fun testFeetToMillimetersConversion() {
        val feetMeasurement = Measurement.Feet(1.0)
        Assert.assertEquals(304.8, feetMeasurement.millimeters, 0.001)
    }

    @Test
    fun testYardsToMillimetersConversion() {
        val yardsMeasurement = Measurement.Yards(1.0)
        Assert.assertEquals(914.4, yardsMeasurement.millimeters, 0.001)
    }

    @Test
    fun testMillimetersToMillimetersConversion() {
        val millimetersMeasurement = Measurement.Millimeters(100.0)
        Assert.assertEquals(100.0, millimetersMeasurement.millimeters, 0.001)
    }

    @Test
    fun testCentimetersToMillimetersConversion() {
        val centimetersMeasurement = Measurement.Centimeters(10.0)
        Assert.assertEquals(100.0, centimetersMeasurement.millimeters, 0.001)
    }

    @Test
    fun testDecimetersToMillimetersConversion() {
        val decimetersMeasurement = Measurement.Decimeters(1.0)
        Assert.assertEquals(100.0, decimetersMeasurement.millimeters, 0.001)
    }

    @Test
    fun testMetersToMillimetersConversion() {
        val metersMeasurement = Measurement.Meters(1.0)
        Assert.assertEquals(1000.0, metersMeasurement.millimeters, 0.001)
    }

    @Test
    fun testFieldsMeasurement() {
        val sideLength = Measurement.Meters(2.0)
        val fieldsMeasurement = Measurement.Fields(3.0, sideLength)
        Assert.assertEquals(6000.0, fieldsMeasurement.millimeters, 0.001)
    }

    @Test
    fun testInchesConversion() {
        val millimeters = 100.0
        val measurement = Measurement.Millimeters(millimeters)
        Assert.assertEquals(3.937, measurement.inches, 0.001)
    }

    @Test
    fun testPlusOperator() {
        val measurement1 = Measurement.Millimeters(100.0)
        val measurement2 = Measurement.Millimeters(50.0)
        val result = measurement1 + measurement2
        Assert.assertEquals(150.0, result.millimeters, 0.001)
    }

    @Test
    fun testMinusOperator() {
        val measurement1 = Measurement.Millimeters(100.0)
        val measurement2 = Measurement.Millimeters(50.0)
        val result = measurement1 - measurement2
        Assert.assertEquals(50.0, result.millimeters, 0.001)
    }

    @Test
    fun testTimesOperator() {
        val measurement = Measurement.Millimeters(100.0)
        val factor = 2.0
        val result = measurement * factor
        Assert.assertEquals(200.0, result.millimeters, 0.001)
    }

    @Test
    fun testDivideOperator() {
        val measurement = Measurement.Millimeters(100.0)
        val divisor = 2.0
        val result = measurement / divisor
        Assert.assertEquals(50.0, result.millimeters, 0.001)
    }

    @Test
    fun testModulusOperator() {
        val measurement = Measurement.Millimeters(105.0)
        val modulus = 10.0
        val result = measurement % modulus
        Assert.assertEquals(5.0, result.millimeters, 0.001)
    }

    @Test
    fun testCompareTo() {
        val measurement1 = Measurement.Millimeters(100.0)
        val measurement2 = Measurement.Millimeters(50.0)
        Assert.assertTrue(measurement1 > measurement2)
    }
}