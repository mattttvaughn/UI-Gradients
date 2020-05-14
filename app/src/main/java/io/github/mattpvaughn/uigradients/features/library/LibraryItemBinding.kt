package io.github.mattpvaughn.uigradients.features.library

import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.GradientListItemBinding
import io.github.mattpvaughn.uigradients.util.toColorInt
import io.github.mattpvaughn.uigradients.view.toGradientDrawable

fun GradientListItemBinding.bind(gradient: Gradient) {
    title.text = gradient.name
    this.card.background = gradient.toGradientDrawable()
}

