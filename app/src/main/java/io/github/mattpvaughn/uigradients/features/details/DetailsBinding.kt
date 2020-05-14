package io.github.mattpvaughn.uigradients.features.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import io.github.mattpvaughn.uigradients.R
import io.github.mattpvaughn.uigradients.application.APP_NAME
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.FragmentDetailsBinding
import io.github.mattpvaughn.uigradients.util.toColorInt
import io.github.mattpvaughn.uigradients.view.toGradientDrawable

/**
 * Turned into a big mess. Ideally I would at least extract the color button that goes into
 * [FragmentDetailsBinding.colorButtonContainer] into an xml file and create it programmatically
 * with ViewBinding here. It would probably be a waste of time to create a whole RecyclewView for
 * the color buttons alone
 */
fun FragmentDetailsBinding.bind(gradient: Gradient) {
    this.name.text = gradient.name
    this.gradientBackground.background = gradient.toGradientDrawable()
    this.colorButtonContainer.removeAllViews()
    val clipboard: ClipboardManager =
        root.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    for (color in gradient.colors) {
        val colorTextButton = Button(root.context).apply {
            val buttonSpacing = root.context.resources.getDimension(R.dimen.spacing_normal).toInt()
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                topMargin = buttonSpacing
            }
            val padding = root.context.resources.getDimension(R.dimen.button_padding).toInt()
            setPadding(padding, padding, padding, padding)
            text = color
            setOnClickListener {
                val clip: ClipData = ClipData.newPlainText("color", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(root.context, "Copied color: $text", Toast.LENGTH_SHORT).show()
            }
            compoundDrawablePadding = padding
            setCompoundDrawables(
                root.resources.getDrawable(R.drawable.ic_content_copy_white_24dp),
                null,
                null,
                null
            )
            setBackgroundColor(color.toColorInt())
        }
        this.colorButtonContainer.addView(colorTextButton)
    }

}

