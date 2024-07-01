package com.aliumujib.designsystem.theme

import androidx.annotation.FloatRange
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(15.dp)
)

private fun RoundedPolygon.Companion.squircle(
    width: Float,
    height: Float,
    cornerRadius: Float,
    @FloatRange(from = 0.0, to = 1.0) smoothing: Float,
): android.graphics.Path {
    if (width == 0f || height == 0f) {
        return android.graphics.Path()
    }
    @Suppress("ktlint:standard:argument-list-wrapping")
    return RoundedPolygon(
        vertices = floatArrayOf(
            0f, 0f,
            width, 0f,
            width, height,
            0f, height,
        ),
        rounding = CornerRounding(cornerRadius, smoothing),
    ).toPath()
}

internal class FigmaShape(
    private val radius: Dp,
    @FloatRange(from = 0.0, to = 1.0) private val smoothing: Float = 1f,
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val squirclePath = RoundedPolygon.squircle(
            width = size.width,
            height = size.height,
            cornerRadius = with(density) { radius.toPx() },
            smoothing = smoothing,
        )
        return Outline.Generic(squirclePath.asComposePath())
    }
}

val Shapes.squircleMedium: Shape get() = SquircleMedium
private val SquircleMedium = FigmaShape(12.dp)

/**
 * Turns the shape into one where only the top corners apply, by combining the path with a square path at the bottom.
 */
private fun Shape.top(): Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val existingShapePath = (this@top.createOutline(size, layoutDirection, density) as Outline.Generic).path
        val flatBottomShape = Path().apply {
            moveTo(0f, size.height / 2)
            lineTo(0f, size.height)
            lineTo(size.width, size.height)
            lineTo(size.width, size.height / 2)
            close()
        }
        return Outline.Generic(
            Path.combine(
                operation = PathOperation.Union,
                path1 = flatBottomShape,
                path2 = existingShapePath,
            ),
        )
    }
}
