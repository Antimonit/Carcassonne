package me.khol.carcassonne.ui

import kotlin.test.Test
import kotlin.test.assertEquals

class ShortestAngleTest {

    @Test
    fun `shortestAngle does not rotate on 0 degree turns`() {
        assertEquals(expected = 0f, actual = shortestAngle(current = 0f, target = 0f))
        assertEquals(expected = 90f, actual = shortestAngle(current = 90f, target = 90f))
        assertEquals(expected = 180f, actual = shortestAngle(current = 180f, target = 180f))
        assertEquals(expected = 270f, actual = shortestAngle(current = 270f, target = 270f))
    }

    @Test
    fun `shortestAngle rotates clockwise on 90 degree turns`() {
        assertEquals(expected = 360f, actual = shortestAngle(current = 270f, target = 0f))
        assertEquals(expected = 90f, actual = shortestAngle(current = 0f, target = 90f))
        assertEquals(expected = 180f, actual = shortestAngle(current = 90f, target = 180f))
        assertEquals(expected = 270f, actual = shortestAngle(current = 180f, target = 270f))
    }

    @Test
    fun `shortestAngle rotates clockwise on 179 degree turns`() {
        assertEquals(expected = 360f, actual = shortestAngle(current = 180f, target = 0f))
        assertEquals(expected = 450f, actual = shortestAngle(current = 270f, target = 90f))
        assertEquals(expected = 180f, actual = shortestAngle(current = 0f, target = 180f))
        assertEquals(expected = 270f, actual = shortestAngle(current = 90f, target = 270f))
    }

    @Test
    fun `shortestAngle rotates clockwise on 180 degree turns`() {
        assertEquals(expected = 360f, actual = shortestAngle(current = 180f, target = 0f))
        assertEquals(expected = 450f, actual = shortestAngle(current = 270f, target = 90f))
        assertEquals(expected = 180f, actual = shortestAngle(current = 0f, target = 180f))
        assertEquals(expected = 270f, actual = shortestAngle(current = 90f, target = 270f))
    }

    @Test
    fun `shortestAngle rotates counter clockwise on 181 degree turns`() {
        assertEquals(expected = 0f, actual = shortestAngle(current = 179f, target = 0f))
        assertEquals(expected = 90f, actual = shortestAngle(current = 269f, target = 90f))
        assertEquals(expected = 180f, actual = shortestAngle(current = 359f, target = 180f))
        assertEquals(expected = -90f, actual = shortestAngle(current = 89f, target = 270f))
    }

    @Test
    fun `shortestAngle rotates counter clockwise on 270 degree turns`() {
        assertEquals(expected = 0f, actual = shortestAngle(current = 90f, target = 0f))
        assertEquals(expected = 90f, actual = shortestAngle(current = 180f, target = 90f))
        assertEquals(expected = 180f, actual = shortestAngle(current = 270f, target = 180f))
        assertEquals(expected = -90f, actual = shortestAngle(current = 0f, target = 270f))
    }
}
