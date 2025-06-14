package me.khol.carcassonne

import kotlin.test.Test
import kotlin.test.assertEquals

class PositionTest {

    @Test
    fun `rotate edge by 90`() {
        assertEquals(ElementPosition.Edge.Right, ElementPosition.Edge.Top.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.Edge.Bottom, ElementPosition.Edge.Right.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.Edge.Left, ElementPosition.Edge.Bottom.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.Edge.Top, ElementPosition.Edge.Left.rotate(Rotation.ROTATE_90))
    }

    @Test
    fun `rotate bottom edge`() {
        assertEquals(ElementPosition.Edge.Bottom, ElementPosition.Edge.Bottom.rotate(Rotation.ROTATE_0))
        assertEquals(ElementPosition.Edge.Left, ElementPosition.Edge.Bottom.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.Edge.Top, ElementPosition.Edge.Bottom.rotate(Rotation.ROTATE_180))
        assertEquals(ElementPosition.Edge.Right, ElementPosition.Edge.Bottom.rotate(Rotation.ROTATE_270))
    }

    @Test
    fun `rotate splitEdge by 90`() {
        assertEquals(ElementPosition.SplitEdge.RightTop, ElementPosition.SplitEdge.TopLeft.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.RightBottom, ElementPosition.SplitEdge.TopRight.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.BottomRight, ElementPosition.SplitEdge.RightTop.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.BottomLeft, ElementPosition.SplitEdge.RightBottom.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.LeftBottom, ElementPosition.SplitEdge.BottomRight.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.LeftTop, ElementPosition.SplitEdge.BottomLeft.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.TopLeft, ElementPosition.SplitEdge.LeftBottom.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.TopRight, ElementPosition.SplitEdge.LeftTop.rotate(Rotation.ROTATE_90))
    }

    @Test
    fun `rotate bottom splitEdge`() {
        assertEquals(ElementPosition.SplitEdge.BottomRight, ElementPosition.SplitEdge.BottomRight.rotate(Rotation.ROTATE_0))
        assertEquals(ElementPosition.SplitEdge.BottomLeft, ElementPosition.SplitEdge.BottomLeft.rotate(Rotation.ROTATE_0))
        assertEquals(ElementPosition.SplitEdge.LeftBottom, ElementPosition.SplitEdge.BottomRight.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.LeftTop, ElementPosition.SplitEdge.BottomLeft.rotate(Rotation.ROTATE_90))
        assertEquals(ElementPosition.SplitEdge.TopLeft, ElementPosition.SplitEdge.BottomRight.rotate(Rotation.ROTATE_180))
        assertEquals(ElementPosition.SplitEdge.TopRight, ElementPosition.SplitEdge.BottomLeft.rotate(Rotation.ROTATE_180))
        assertEquals(ElementPosition.SplitEdge.RightTop, ElementPosition.SplitEdge.BottomRight.rotate(Rotation.ROTATE_270))
        assertEquals(ElementPosition.SplitEdge.RightBottom, ElementPosition.SplitEdge.BottomLeft.rotate(Rotation.ROTATE_270))
    }
}