package io.github.mattpvaughn.uigradients.util

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/** Methods for handling color operations which aren't handled by androidX's ColorUtils */

/** Colors an #RRGGBB or #RGB string to [ColorInt] */
fun String.toColorInt(): Int {
    var s = if (!startsWith("#")) {
        "#$this"
    } else {
        this
    }
    // Convert type #ABC to #AABBCC
    if (s.length == 4) {
        s = "#${s[1]}${s[1]}${s[2]}${s[2]}${s[3]}${s[3]}"
    }
    return Color.parseColor(s)
}

/**
 * Given a background color [bg], pick a foreground color to match it. Prefer [fg] if it has enough
 * contrast, otherwise return [fgBackup].
 *
 * Assume that [fgBackup] will a high enough contrast if [fg] does not
 */
fun pickForegroundColor(@ColorInt bg: Int, @ColorInt fg: Int, @ColorInt fgBackup: Int): Int {
    val contrast1 = ColorUtils.calculateContrast(fg, bg)
    // Experimentally determined to be pretty good threshold
    return if (contrast1 > 1.41) {
        fg
    } else {
        fgBackup
    }
}
