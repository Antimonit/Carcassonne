package me.khol.carcassonne

import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class PositionTest {

    @Test
    fun `rotate edge by 90`() {
        expect {
            that(Position.Edge.Top).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.Edge.Right)
            that(Position.Edge.Right).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.Edge.Bottom)
            that(Position.Edge.Bottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.Edge.Left)
            that(Position.Edge.Left).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.Edge.Top)
        }
    }

    @Test
    fun `rotate bottom edge`() {
        expect {
            that(Position.Edge.Bottom).get { rotate(Rotation.ROTATE_0) }.isEqualTo(Position.Edge.Bottom)
            that(Position.Edge.Bottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.Edge.Left)
            that(Position.Edge.Bottom).get { rotate(Rotation.ROTATE_180) }.isEqualTo(Position.Edge.Top)
            that(Position.Edge.Bottom).get { rotate(Rotation.ROTATE_270) }.isEqualTo(Position.Edge.Right)
        }
    }

    @Test
    fun `rotate splitEdge by 90`() {
        expect {
            that(Position.SplitEdge.TopLeft).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.RightTop)
            that(Position.SplitEdge.TopRight).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.RightBottom)
            that(Position.SplitEdge.RightTop).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.BottomRight)
            that(Position.SplitEdge.RightBottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.BottomLeft)
            that(Position.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.LeftBottom)
            that(Position.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.LeftTop)
            that(Position.SplitEdge.LeftBottom).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.TopLeft)
            that(Position.SplitEdge.LeftTop).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.TopRight)
        }
    }

    @Test
    fun `rotate bottom splitEdge`() {
        expect {
            that(Position.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_0) }.isEqualTo(Position.SplitEdge.BottomRight)
            that(Position.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_0) }.isEqualTo(Position.SplitEdge.BottomLeft)
            that(Position.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.LeftBottom)
            that(Position.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_90) }.isEqualTo(Position.SplitEdge.LeftTop)
            that(Position.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_180) }.isEqualTo(Position.SplitEdge.TopLeft)
            that(Position.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_180) }.isEqualTo(Position.SplitEdge.TopRight)
            that(Position.SplitEdge.BottomRight).get { rotate(Rotation.ROTATE_270) }.isEqualTo(Position.SplitEdge.RightTop)
            that(Position.SplitEdge.BottomLeft).get { rotate(Rotation.ROTATE_270) }.isEqualTo(Position.SplitEdge.RightBottom)
        }
    }
}