package me.khol.carcassonne

import me.khol.carcassonne.Element.City
import me.khol.carcassonne.Element.Field
import me.khol.carcassonne.Element.Monastery
import me.khol.carcassonne.Element.Road
import kotlin.test.Test
import kotlin.test.assertEquals

class ElementsTest {

    private val city = City { top }
    private val fieldTop = Field(city) { leftTop + rightTop }
    private val fieldBottom = Field { leftBottom + rightBottom + bottom }
    private val road = Road { left + right }

    private val elements = elements {
        add(city)
        add(fieldTop)
        add(fieldBottom)
        add(road)
    }

    @Test
    fun `check cities`() {
        assertEquals(listOf(city), elements[City])
    }

    @Test
    fun `check roads`() {
        assertEquals(listOf(road), elements[Road])
    }

    @Test
    fun `check fields`() {
        assertEquals(listOf(fieldTop, fieldBottom), elements[Field])
    }

    @Test
    fun `check monasteries`() {
        assertEquals(listOf(), elements[Monastery])
    }

    @Test
    fun `check all`() {
        assertEquals(listOf(city, fieldTop, fieldBottom, road), elements.all())
    }
}
