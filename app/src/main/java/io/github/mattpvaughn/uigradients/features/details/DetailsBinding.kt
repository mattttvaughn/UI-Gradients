package io.github.mattpvaughn.uigradients.features.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.widget.Toast
import io.github.mattpvaughn.uigradients.R
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.CopyColorButtonBinding
import io.github.mattpvaughn.uigradients.databinding.FragmentDetailsBinding
import io.github.mattpvaughn.uigradients.util.pickForegroundColor
import io.github.mattpvaughn.uigradients.util.toColorInt
import io.github.mattpvaughn.uigradients.view.getMedianColor
import io.github.mattpvaughn.uigradients.view.toGradientDrawable

/**
 * Binds data from [gradient] to the layout [FragmentDetailsBinding].
 *
 * Note: the logic for copying here maybe ought to be in the ViewModel
 */
fun FragmentDetailsBinding.bind(gradient: Gradient) {
    val clipboard: ClipboardManager =
        root.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val inflater = this.root.context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

    root.background = gradient.toGradientDrawable()

    val textColorInverse = root.context.resources.getColor(
        R.color.textPrimaryDark, root.context.theme
    )
    val textColorPrimary = root.context.resources.getColor(
        R.color.textPrimary, root.context.theme
    )
    val backgroundColor = gradient.getMedianColor()
    val foregroundColor = pickForegroundColor(backgroundColor, textColorPrimary, textColorInverse)
    toolbar.title = gradient.name
    toolbar.setTitleTextColor(foregroundColor)
    toolbar.navigationIcon?.setTint(foregroundColor)

    // Make buttons for each color. Data set has a maximum of ~5 colors per gradient, so
    // RecyclerView wouldn't give any performance benefit
    this.colorButtonContainer.removeAllViews()
    for (color in gradient.colors) {
        val button = CopyColorButtonBinding.inflate(inflater, colorButtonContainer, false)
        button.root.text = color
        val buttonForeground = pickForegroundColor(color.toColorInt(), textColorPrimary, textColorInverse)
        button.root.compoundDrawableTintList = ColorStateList.valueOf(buttonForeground)
        button.root.setTextColor(buttonForeground)
        button.root.setOnClickListener {
            val clip: ClipData = ClipData.newPlainText("color", color)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(root.context, "Copied color: $color", Toast.LENGTH_SHORT).show()
        }
        button.root.setBackgroundColor(color.toColorInt())
        colorButtonContainer.addView(button.root)
    }
}

