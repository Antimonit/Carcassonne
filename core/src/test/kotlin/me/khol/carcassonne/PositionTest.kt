package me.khol.carcassonne

import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class PositionTest {

    @Test
    fun `rotate edge by 90`() {
        expect {
            that(ElementPosition.Edge.Top).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.Edge.Right)
            that(ElementPosition.Edge.Right).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.Edge.Bottom)
            that(ElementPosition.Edge.Bottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.Edge.Left)
            that(ElementPosition.Edge.Left).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.Edge.Top)
        }
    }

    @Test
    fun `rotate bottom edge`() {
        expect {
            that(ElementPosition.Edge.Bottom).get { rotate(Rotation.ROTATE_0) }.isEqualTo(ElementPosition.Edge.Bottom)
            that(ElementPosition.Edge.Bottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.Edge.Left)
            that(ElementPosition.Edge.Bottom).get { rotate(Rotation.ROTATE_180) }.isEqualTo(ElementPosition.Edge.Top)
            that(ElementPosition.Edge.Bottom).get { rotate(Rotation.ROTATE_270) }.isEqualTo(ElementPosition.Edge.Right)
        }
    }

    @Test
    fun `rotate splitEdge by 90`() {
        expect {
            that(ElementPosition.SplitEdge.TopLeft).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.RightTop)
            that(ElementPosition.SplitEdge.TopRight).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.RightBottom)
            that(ElementPosition.SplitEdge.RightTop).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.BottomRight)
            that(ElementPosition.SplitEdge.RightBottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.BottomLeft)
            that(ElementPosition.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.LeftBottom)
            that(ElementPosition.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.LeftTop)
            that(ElementPosition.SplitEdge.LeftBottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.TopLeft)
            that(ElementPosition.SplitEdge.LeftTop).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.TopRight)
        }
    }

    @Test
    fun `rotate bottom splitEdge`() {
        expect {
            that(ElementPosition.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_0) }.isEqualTo(ElementPosition.SplitEdge.BottomRight)
            that(ElementPosition.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_0) }.isEqualTo(ElementPosition.SplitEdge.BottomLeft)
            that(ElementPosition.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.LeftBottom)
            that(ElementPosition.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_90) }.isEqualTo(ElementPosition.SplitEdge.LeftTop)
            that(ElementPosition.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_180) }.isEqualTo(ElementPosition.SplitEdge.TopLeft)
            that(ElementPosition.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_180) }.isEqualTo(ElementPosition.SplitEdge.TopRight)
            that(ElementPosition.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_270) }.isEqualTo(ElementPosition.SplitEdge.RightTop)
            that(ElementPosition.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_270) }.isEqualTo(ElementPosition.SplitEdge.RightBottom)
        }
    }
}