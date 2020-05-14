package io.github.mattpvaughn.uigradients.view

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable.ShaderFactory
import android.graphics.drawable.shapes.RectShape
import com.squareup.moshi.internal.Util
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.util.toColorInt


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

fun Float.rangeTo(other: Float, numSteps: Int): FloatArray {
    require(this < other)
    var acc = this
    val step: Float = (other - this) / numSteps
    val list = mutableListOf<Float>()
    while (acc < other) {
        list.add(acc)
        acc += step
    }
    return list.toFloatArray()
}


