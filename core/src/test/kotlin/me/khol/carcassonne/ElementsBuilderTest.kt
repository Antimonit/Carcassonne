package me.khol.carcassonne

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ElementsBuilderTest {

    @Test
    fun `adding elements in two steps is the same as adding in a single step`() {
        val twoStep = elements {
            add(
                Element.City,
                ElementGroup.city { left },
                ElementGroup.city { right },
            )
        }
        val singleStep = elements {
            city { left }
            city { right }
        }
        expectThat(twoStep).isEqualTo(singleStep)
    }
}
