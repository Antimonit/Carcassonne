package me.khol.carcassonne

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class NextOfTest {

    val list = listOf("A", "B", "C")

    @Test
    fun `next element`() {
        assertEquals("B", list.nextOf("A"))
        assertEquals("C", list.nextOf("B"))
        assertEquals("A", list.nextOf("C"))
    }

    @Test
    fun `next of unknown element throws`() {
        assertFails {
            list.nextOf("D")
        }
    }
}
