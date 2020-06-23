package io.github.mattpvaughn.uigradients.view

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable.ShaderFactory
import android.graphics.drawable.shapes.RectShape
import androidx.core.graphics.ColorUtils
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.util.rangeTo
import io.github.mattpvaughn.uigradients.util.toColorInt

/**
 * Converts a [Gradient] to a [Drawable] where the [Drawable] will contain the [Gradient] drawn
 * from the top-left to the bottom-right, with colors in the order provided by [Gradient.colors]
 */
fun Gradient.toGradientDrawable(): Drawable {
    val shaderFactory: ShaderFactory = object : ShaderFactory() {
        override fun resize(width: Int, height: Int): Shader {
            return LinearGradient(
                0F,
                0F,
                width.toFloat(),
                height.toFloat(),
                colors.map { it.toColorInt() }.toIntArray(),
                0F.rangeTo(1F, colors.size),
                Shader.TileMode.REPEAT
            )
        }
    }
    val paint = PaintDrawable()
    paint.shape = RectShape()
    paint.shaderFactory = shaderFactory
    return paint
}

/**
 * Returns the median color in a gradient, using ordering provided by [Gradient.colors]. If there
 * are an even number of colors, then it averages the two median values
 */
fun Gradient.getMedianColor(): Int {
    return if (colors.size % 2 == 1) {
        colors[colors.size / 2].toColorInt()
    } else {
        // avg colors between the two colors surrounding the center
        val hslArrays = listOf(FloatArray(3), FloatArray(3), FloatArray(3))
        ColorUtils.colorToHSL(colors[colors.size / 2 - 1].toColorInt(), hslArrays[0])
        ColorUtils.colorToHSL(colors[colors.size / 2].toColorInt(), hslArrays[1])
        ColorUtils.blendHSL(hslArrays[0], hslArrays[1], 0.5F, hslArrays[2])
        ColorUtils.HSLToColor(hslArrays[2])
    }
}

