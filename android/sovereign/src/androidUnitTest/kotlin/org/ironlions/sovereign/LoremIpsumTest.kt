package org.ironlions.sovereign

import org.junit.Test
import org.junit.Assert.*

class LoremIpsumTest {
    @Test
    fun loremIpsumSitDolorAmet_isCorrect() {
        val lorem = LoremIpsum('!')
        assertEquals(lorem.dolor, "sit amet!")
    }
}