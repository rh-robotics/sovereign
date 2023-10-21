package org.ironlions.sovereign.math

fun <T : Comparable<T>> rangesOverlap(range1: ClosedRange<T>, range2: ClosedRange<T>): Boolean {
    // Ensure the ranges are sorted from least to greatest
    val sortedRange1 = if (range1.start <= range1.endInclusive) range1 else range1.endInclusive..range1.start
    val sortedRange2 = if (range2.start <= range2.endInclusive) range2 else range2.endInclusive..range2.start

    // Check if the ranges overlap
    return sortedRange1.endInclusive >= sortedRange2.start && sortedRange2.endInclusive >= sortedRange1.start
}
