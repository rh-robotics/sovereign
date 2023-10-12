package org.rowlandhall.ftc.sovereign

import org.ironlions.sovereign.LoremIpsum
import org.junit.Test
import org.junit.Assert.*

class LoremIpsumTest {
    @Test
    fun loremIpsumSitDolorAmet_isCorrect() {
        val lorem = LoremIpsum('!')
        assertEquals(lorem.dolor, "sit amet!")
    }
}