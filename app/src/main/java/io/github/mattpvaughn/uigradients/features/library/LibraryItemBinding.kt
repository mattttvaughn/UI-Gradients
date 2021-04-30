package io.github.mattpvaughn.uigradients.features.library

import io.github.mattpvaughn.uigradients.R
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.GradientListItemBinding
import io.github.mattpvaughn.uigradients.util.pickForegroundColor
import io.github.mattpvaughn.uigradients.view.getMedianColor
import io.github.mattpvaughn.uigradients.view.toGradientDrawable

/**
 * Binds a [Gradient] to a [GradientListItemBinding], reflecting the appearance and name of
 * the gradient in the view
 */
fun GradientListItemBinding.bind(gradient: Gradient) {
    title.text = gradient.name
    thumb.background = gradient.toGradientDrawable()

    val textColorInverse = root.context.resources.getColor(
        R.color.textPrimaryDark, root.context.theme
    )
    val textColorPrimary= root.context.resources.getColor(
        R.color.textPrimary, root.context.theme
    )
    val backgroundColor = gradient.getMedianColor()
    val foregroundColor = pickForegroundColor(backgroundColor, textColorPrimary, textColorInverse)
    title.setTextColor(foregroundColor)
}

